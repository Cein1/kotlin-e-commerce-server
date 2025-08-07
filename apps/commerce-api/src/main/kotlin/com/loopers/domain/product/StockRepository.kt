package com.loopers.domain.product

import java.util.Optional

interface StockRepository {
    fun save(stock: StockModel): StockModel
    fun findLatestByRefProductId(productId: Long): Optional<StockModel>
}
