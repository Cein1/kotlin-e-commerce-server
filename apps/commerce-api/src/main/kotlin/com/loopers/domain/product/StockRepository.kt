package com.loopers.domain.product

interface StockRepository {
    fun save(stock: StockModel): StockModel
    fun findLatestByProductId(productId: Long): StockModel?
}
