package com.loopers.infrastructure.like

import com.loopers.domain.like.LikeModel
import com.loopers.domain.like.LikeRepository
import com.loopers.domain.like.QLikeModel.likeModel
import com.querydsl.jpa.impl.JPAQueryFactory

class LikeRepositoryImpl(
    private val likeJpaRepository: LikeJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : LikeRepository {
    override fun getMyLikedProductIds(userId: Long): List<Long> {
        return queryFactory.select(likeModel.id.productId)
            .from(likeModel)
            .where(likeModel.id.userId.eq(userId))
            .distinct()
            .fetch()
    }

    override fun countByProductId(productId: Long): Long {
        return queryFactory.select(likeModel.count())
            .from(likeModel)
            .where(likeModel.id.productId.eq(productId))
            .fetchOne() ?: 0L
    }

    override fun countByProductIds(productIds: List<Long>): Map<Long, Long> {
        return queryFactory
            .select(likeModel.id.productId, likeModel.count())
            .from(likeModel)
            .where(likeModel.id.productId.`in`(productIds))
            .groupBy(likeModel.id.productId)
            .fetch()
            .associate { tuple ->
                val productId = tuple.get(likeModel.id.productId) ?: 0L
                val count = tuple.get(likeModel.count()) ?: 0L
                productId to count
            }
    }

    override fun delete(like: LikeModel) {
        likeJpaRepository.delete(like)
    }

    override fun save(like: LikeModel): LikeModel {
        return likeJpaRepository.save(like)
    }

    override fun exist(
        userId: Long,
        productId: Long,
    ): LikeModel? {
        TODO("Not yet implemented")
    }
}
