package com.loopers.infrastructure.brand

// import com.loopers.domain.brand.BrandModel
import com.loopers.domain.brand.BrandModel
import com.loopers.domain.brand.BrandRepository
import com.loopers.domain.brand.QBrandModel.brandModel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class BrandRepositoryImpl(
    private val brandJpaRepository: BrandJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : BrandRepository {
    override fun findByIds(ids: Set<Long>): Map<Long, BrandModel> {
        return queryFactory.selectFrom(brandModel)
            .where(brandModel.id.`in`(ids))
            .fetch()
            .associateBy { it.id }
    }

    override fun findById(id: Long): Optional<BrandModel> {
        return brandJpaRepository.findById(id)
    }

    override fun existsById(id: Long): Boolean {
        return brandJpaRepository.existsById(id)
    }

    override fun save(brand: BrandModel): BrandModel {
        return brandJpaRepository.save(brand)
    }
}
