package com.loopers.application.product

import com.loopers.domain.brand.BrandService
import com.loopers.domain.like.LikeService
import com.loopers.domain.product.ProductService

class ProductFacade(
    private val productService: ProductService,
    private val likesService: LikeService,
    private val brandService: BrandService,
) {
    /**
     * /api/v1/products 상품 목록 조회
     */
    fun getProducts(
        page: Int,
        size: Int,
        sortType: String,
    ): List<ProductInfo.ProductListItem> {
        val products = productService.getProducts(page, size, sortType)
        val productsIds = products.map { it.id }

        val likesCountMap = likesService.getLikesCountForProducts(productsIds)
        val brandsMap = brandService.getBrandsByIds(products.map { it.refBrandId }.toSet())
        return products.map { product ->
            ProductInfo.ProductListItem(
                id = product.id,
                refBrandId = product.refBrandId,
                brandName = brandsMap[product.refBrandId]?.name ?: "Unknown",
                name = product.name,
                price = product.price,
                thumbnail = product.thumbnail,
                likesCount = likesCountMap[product.id] ?: 0,
            )
        }
    }

    /**
     * /api/v1/products 상품 정보 조회
     */
    fun getProduct(
        productId: Long,
    ): ProductInfo.ProductDetail {
        val product = productService.getProductDetail(productId)
        val likesCount = likesService.getLikesCount(productId)
        val brand = brandService.getBrandsById(product.refBrandId)
        return ProductInfo.ProductDetail(
            id = product.id,
            refBrandId = product.refBrandId,
            brandName = brand?.name ?: "Unknown",
            brandDescription = brand?.description ?: "Unknown",
            name = product.name,
            price = product.price,
            thumbnail = product.thumbnail,
            likesCount = likesCount,
        )
    }
}
