package com.loopers.domain.product

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component

@Component
class StockService(
    private val stockRepository: StockRepository,
) {
    fun getAvailableStockQuantity(productId: Long): Long {
        val latestLog = stockRepository.findLatestByRefProductId(productId)
        return latestLog.map { it.available }.orElse(0L)
    }

    fun writeOutboundStock(
        productId: Long,
        quantity: Long,
    ) {
        val currentAvailable = getAvailableStockQuantity(productId)
        if (currentAvailable < quantity) {
            throw CoreException(
                ErrorType.BAD_REQUEST,
                "재고가 부족합니다. 현재 재고: $currentAvailable, 요청 수량: $quantity",
            )
        }

        val newAvailable = currentAvailable - quantity
        val newOutboundStock = StockModel(
            refProductId = productId,
            inbound = null,
            outbound = quantity,
            available = newAvailable,
        )

        stockRepository.save(newOutboundStock)
    }

    /**
    fun writeInboundStock(
        productId: Long,
        quantity: Long,
    ) {
        val currentAvailable = getAvailableStockQuantity(productId)
        val newAvailable = currentAvailable + quantity
        val newInboundStock = StockModel(
            refProductId = productId,
            inbound = quantity,
            outbound = null,
            available = newAvailable,
        )

        stockRepository.save(newInboundStock)
    }
    */
}
