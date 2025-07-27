package com.loopers.domain.like

class LikeService(private val likeRepository: LikeRepository) {
    fun addLike(userId: Long, productId: Long): Boolean {
        if (likeRepository.exist(userId, productId) != null) {
            return false
        }

        val newLike = LikeModel(userId, productId)
        likeRepository.save(newLike)
        return true
    }

    fun cancelLike(userId: Long, productId: Long): Boolean {
        val existingLike = likeRepository.exist(userId, productId)
        return if (existingLike != null) {
            likeRepository.delete(existingLike)
            true
        } else {
            false
        }
    }

    fun getLikesCount(productId: Long): Long {
        return likeRepository.countByProductId(productId)
    }

    fun getLikesCountForProducts(productIds: List<Long>): Map<Long, Long> {
        return likeRepository.countByProductIds(productIds)
    }
}
