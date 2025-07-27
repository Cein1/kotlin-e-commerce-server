package com.loopers.infrastructure.brand

// import com.loopers.domain.brand.BrandModel
import com.loopers.domain.brand.BrandModel
import com.loopers.domain.brand.BrandRepository
import com.loopers.domain.brand.QBrandModel.brandModel
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component

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

    override fun findById(id: Long): BrandModel? {
        return brandJpaRepository.findById(id).orElse(null)
    }

    override fun existsById(id: Long): Boolean {
        TODO("Not yet implemented")
    }

    override fun save(brand: BrandModel): BrandModel {
        TODO("Not yet implemented")
    }
}
