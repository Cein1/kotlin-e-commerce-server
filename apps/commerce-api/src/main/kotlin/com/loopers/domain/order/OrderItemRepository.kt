package com.loopers.domain.order

interface OrderItemRepository {
    fun saveAll(orderItemModels: List<OrderItemModel>): MutableList<OrderItemModel>
}
