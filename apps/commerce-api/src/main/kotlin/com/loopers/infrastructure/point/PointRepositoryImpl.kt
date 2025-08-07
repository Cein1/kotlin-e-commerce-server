package com.loopers.infrastructure.point

import com.loopers.domain.point.PointModel
import com.loopers.domain.point.PointRepository
import com.loopers.domain.point.QPointModel.pointModel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class PointRepositoryImpl(
    private val pointJpaRepository: PointJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : PointRepository {
    override fun findByRefUserId(refUserId: Long): Optional<PointModel> {
        return pointJpaRepository.findByRefUserId(refUserId)
    }

    override fun getPointLog(refUserId: Long): PointModel? {
        return queryFactory
            .selectFrom(pointModel)
            .where(pointModel.refUserId.eq(refUserId))
            .orderBy(pointModel.id.desc())
            .fetchFirst()
    }
}
