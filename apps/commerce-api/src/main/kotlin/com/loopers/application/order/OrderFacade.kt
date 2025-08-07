package com.loopers.application.order

import com.loopers.domain.order.OrderService
import com.loopers.domain.payment.PaymentService
import com.loopers.domain.point.PointService
import com.loopers.domain.product.ProductService
import com.loopers.domain.product.StockService
import com.loopers.domain.user.UserService
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import java.math.BigDecimal

class OrderFacade(
    private val userService: UserService,
    private val pointService: PointService,
    private val productService: ProductService,
    private val orderService: OrderService,
    private val stockService: StockService,
    private val paymentService: PaymentService,
) {
    /**
     * /api/v1/orders 주문 요청
     */
    fun order(
        userId: String,
        orderItems: List<OrderItem>,
    ): OrderResult {
        // 총 결제 예정 금액
        var totalAmount: BigDecimal = BigDecimal(0)

        // 사용자 정보 조회
        val user = userService.getUserInfo(userId) ?: throw CoreException(ErrorType.NOT_FOUND, "존재하지 않는 사용자입니다")

        // 상품 정보 조회
        val productIds = orderItems.map { it.productId }
        val products = productService.getProductDetails(productIds)

        // orderItems 순회
        val itemsToBePaid: MutableList<OrderItem> = mutableListOf()
        val itemsOutOfStock: MutableList<OrderItem> = mutableListOf()
        for (item in orderItems) {
            val product = products[item.productId]
                ?: throw CoreException(ErrorType.NOT_FOUND, "상품 정보를 찾을 수 없습니다.")

            // 상품 가격 조회
            val productPrice = product.price
            val productName = product.name
            val productThumbnail = product.thumbnail

            // 상품별 재고 정보 조회
            val availableStock = stockService.getAvailableStockQuantity(item.productId)
            // 주문 가능 수량 확인
            if (availableStock >= item.quantity) {
                // 결제 예정 품목에 추가
                itemsToBePaid.add(OrderItem(item.productId, item.quantity, productPrice, productName, productThumbnail))
                totalAmount += productPrice.multiply(BigDecimal(item.quantity))
            } else {
                itemsOutOfStock.add(OrderItem(item.productId, item.quantity, productPrice, productName, productThumbnail))
            }
        }
        if (itemsToBePaid.isEmpty()) {
            throw CoreException(ErrorType.CONFLICT, "주문 가능한 상품이 없습니다. [사유: 재고 부족]")
        }

        // 포인트 잔액 조회
        val pointBalance = pointService.getPointBalance(user.id)
        if (totalAmount > pointBalance) {
            throw CoreException(ErrorType.CONFLICT, "포인트 잔액이 부족합니다.")
        }

        // 주문 저장
        val order = orderService.writeOrder(user.id, itemsToBePaid)

        // 결제
        val payment = paymentService.writePayment(user.id, order.id, totalAmount, "SUCCESS")

        // 재고 차감
        for (item in itemsToBePaid) {
            stockService.writeOutboundStock(item.productId, item.quantity)
        }
        // 포인트 차감
        val deductPoint = pointService.deductPoint(user.id, totalAmount)

        return OrderResult(orderSuccess = itemsToBePaid, orderFail = itemsOutOfStock)
    }
}

data class OrderItem(
    val productId: Long,
    val quantity: Long,
    val productPrice: BigDecimal,
    val productName: String,
    val thumbnail: String,
)

data class OrderResult(
    val orderSuccess: List<OrderItem>,
    val orderFail: List<OrderItem>,
)
