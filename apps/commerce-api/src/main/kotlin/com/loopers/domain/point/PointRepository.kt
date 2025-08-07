package com.loopers.domain.point

import java.util.Optional

interface PointRepository {
    fun findByRefUserId(refUserId: Long): Optional<PointModel>
    fun getPointLog(refUserId: Long): PointModel?
}
