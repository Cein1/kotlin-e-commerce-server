package com.loopers.domain.product

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

enum class ProductSortType(val sort: Sort) {
    LATEST(
        Sort.by(
            Sort.Direction.DESC,
            "createdAt",
        ),
    ),
    PRICE_ASC(
        Sort.by(
            Sort.Direction.ASC,
            "price",
        ),
    ),
    LIKES_DESC(
        Sort.by(
            Sort.Direction.DESC,
            "likesCount",
        ),
    ), ;

    companion object {
        fun fromString(sortType: String?): ProductSortType {
            return entries.find {
                it.name.equals(
                    sortType,
                    ignoreCase = true,
                )
            } ?: LATEST
        }
    }
}

@Service
class ProductService(private val productRepository: ProductRepository) {
    fun getProducts(
        page: Int,
        size: Int,
        sortType: String,
    ): List<ProductModel> {
        val productSortType = ProductSortType.fromString(sortType)
        val pageable = PageRequest.of(
            page,
            size,
            productSortType.sort,
        )
        return productRepository.findAll(pageable)
    }

    fun getProductDetail(productId: Long): ProductModel {
        return productRepository.findById(productId) ?: throw CoreException(
            ErrorType.NOT_FOUND,
            "상품을 찾을 수 없습니다.",
        )
    }

    fun getProductsByBrand(brandId: Long): List<ProductModel> {
        return productRepository.findAllByBrandId(brandId)
    }
}
