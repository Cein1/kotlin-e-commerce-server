package com.loopers.infrastructure.product

import com.loopers.domain.product.ProductModel
import com.loopers.domain.product.ProductRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.util.Optional

@Component
class ProductRepositoryImpl(
    private val productJpaRepository: ProductJpaRepository,
) : ProductRepository {
    override fun getProductPrice(productId: Long): Optional<BigDecimal> {
        return productJpaRepository.findById(productId).map { it.price }
    }

    override fun findAll(pageable: Pageable): List<ProductModel> {
        return productJpaRepository.findAll(pageable).content
    }

    override fun findAllByRefBrandId(brandId: Long): List<ProductModel> {
        return productJpaRepository.findAllByRefBrandId(brandId)
    }

    override fun findById(productId: Long): Optional<ProductModel> {
        return productJpaRepository.findById(productId)
    }

    override fun save(product: ProductModel): ProductModel {
        return productJpaRepository.save(product)
    }

    override fun findAllByIdIn(ids: Collection<Long>): List<ProductModel> {
        TODO("Not yet implemented")
    }
}
