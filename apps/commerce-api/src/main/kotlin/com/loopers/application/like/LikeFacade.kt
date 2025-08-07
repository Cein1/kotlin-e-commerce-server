package com.loopers.application.like

import com.loopers.application.product.ProductInfo
import com.loopers.domain.brand.BrandService
import com.loopers.domain.like.LikeService
import com.loopers.domain.product.ProductService

class LikeFacade(
    private val likeService: LikeService,
    private val productService: ProductService,
    private val brandService: BrandService,
) {
    /**
     * 상품 좋아요 등록 POST /api/v1/products/{productId}/likes
     */
    fun addLike(
        userId: Long,
        productId: Long,
    ): Boolean {
        return likeService.addLike(
            userId,
            productId,
        )
    }

    /**
     * 상품 좋아요 취소 DELETE /api/v1/products/{productId}/likes
     */
    fun cancelLike(
        userId: Long,
        productId: Long,
    ): Boolean {
        return likeService.cancelLike(
            userId,
            productId,
        )
    }

    /**
     * 내가 좋아요 한 상품 목록 조회 GET /api/v1/users/{userId}/likes
     */
    fun getMyLikedProducts(
        userId: Long,
        page: Int,
        size: Int,
        sortType: String?,
    ): List<ProductInfo.ProductListItem> {
        val likedProductIds = likeService.getMyLikedProductIds(userId)
        val products = productService.getProducts(
            page,
            size,
            sortType,
        )
        val likesCountMap = likeService.getLikesCountForProducts(likedProductIds)
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
}
