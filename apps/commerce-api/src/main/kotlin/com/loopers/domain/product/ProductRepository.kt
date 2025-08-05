package com.loopers.domain.product

import org.springframework.data.domain.Pageable
import java.math.BigDecimal
import java.util.Optional

interface ProductRepository {
    fun findById(productId: Long): Optional<ProductModel>
    fun findAll(pageable: Pageable): List<ProductModel>
    fun findAllByRefBrandId(brandId: Long): List<ProductModel>
    fun save(product: ProductModel): ProductModel
    fun getProductPrice(productId: Long): Optional<BigDecimal>
    fun findAllByIdIn(ids: Collection<Long>): List<ProductModel>
}
