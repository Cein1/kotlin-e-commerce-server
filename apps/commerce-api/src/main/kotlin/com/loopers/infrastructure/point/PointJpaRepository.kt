package com.loopers.infrastructure.point

import com.loopers.domain.point.PointModel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface PointJpaRepository : JpaRepository<PointModel, Long> {
    fun findByRefUserId(refUserId: Long): Optional<PointModel>
}
