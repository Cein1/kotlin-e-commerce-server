package com.loopers.infrastructure.point

import com.loopers.domain.point.PointModel
import com.loopers.domain.point.PointRepository
import org.springframework.stereotype.Repository

@Repository
class PointRepositoryImpl(private val pointJpaRepository: PointJpaRepository) : PointRepository {
    override fun findByUserId(userId: String): PointModel? = pointJpaRepository.findByUserId(userId)
}
