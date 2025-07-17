package com.loopers.domain.point

interface PointRepository {
    fun findByUserId(userId: String): PointModel?
}
