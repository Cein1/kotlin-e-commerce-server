package com.loopers.domain.order

interface OrderRepository {
    fun save(newOrder: OrderModel): OrderModel
}
