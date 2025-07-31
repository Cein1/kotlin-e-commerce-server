package com.loopers.domain.product

import org.springframework.data.domain.Pageable

interface ProductRepository {
    fun findById(id: Long): ProductModel?
    fun findAll(pageable: Pageable): List<ProductModel>
    fun findAllByBrandId(brandId: Long): List<ProductModel>
    fun save(product: ProductModel): ProductModel
}
