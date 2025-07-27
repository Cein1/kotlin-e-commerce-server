package com.loopers.domain.like

interface LikeRepository {
    fun save(like: LikeModel): LikeModel
    fun exist(userId: Long, productId: Long): LikeModel?
    fun delete(like: LikeModel)
    fun countByProductId(productId: Long): Long
    fun countByProductIds(productIds: List<Long>): Map<Long, Long>
}
