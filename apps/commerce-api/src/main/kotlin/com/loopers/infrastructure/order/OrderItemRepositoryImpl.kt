package com.loopers.infrastructure.order

import com.loopers.domain.order.OrderItemModel
import com.loopers.domain.order.OrderItemRepository
import org.springframework.stereotype.Component

@Component
class OrderItemRepositoryImpl(
    private val orderItemJpaRepository: OrderItemJpaRepository,
) : OrderItemRepository {
    override fun saveAll(orderItemModels: List<OrderItemModel>): MutableList<OrderItemModel> {
        return orderItemJpaRepository.saveAll(orderItemModels)
    }
}
