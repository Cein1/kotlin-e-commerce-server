package com.loopers.domain.order

import com.loopers.application.order.OrderItem
import java.math.BigDecimal

class OrderService(
    private val orderRepository: OrderRepository,
    private val orderItemRepository: OrderItemRepository,
) {
    fun writeOrder(
        refUserId: Long,
        itemsToBePaid: List<OrderItem>,
    ): OrderModel {
        val newOrder = OrderModel(
            refUserId = refUserId,
        )
        val savedOrder = orderRepository.save(newOrder)

        val orderItemModels = itemsToBePaid.map { item ->
            OrderItemModel(
                refOrderId = savedOrder.id,
                productName = item.productName,
                thumbnail = item.thumbnail,
                price = item.productPrice,
                quantity = item.quantity,
                amount = item.productPrice.multiply(BigDecimal(item.quantity)),
                "SUCCESS",
            )
        }

        orderItemRepository.saveAll(orderItemModels)
        return savedOrder
    }
}
