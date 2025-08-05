package com.loopers.infrastructure.product

import com.loopers.domain.product.StockModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface StockJpaRepository : JpaRepository<StockModel, Long> {
    fun findLatestByRefProductId(productId: Long): Optional<StockModel>
}
