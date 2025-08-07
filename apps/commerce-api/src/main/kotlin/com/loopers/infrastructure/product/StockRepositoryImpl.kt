package com.loopers.infrastructure.product

import com.loopers.domain.product.QStockModel.stockModel
import com.loopers.domain.product.StockModel
import com.loopers.domain.product.StockRepository
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class StockRepositoryImpl(
    private val stockJpaRepository: StockJpaRepository,
    private val queryFactory: JPAQueryFactory,
) : StockRepository {
    override fun save(stock: StockModel): StockModel {
        return stockJpaRepository.save(stock)
    }

    override fun findLatestByRefProductId(productId: Long): Optional<StockModel> {
        val latestStock = queryFactory
            .selectFrom(stockModel)
            .where(stockModel.refProductId.eq(productId))
            .orderBy(stockModel.id.desc())
            .fetchFirst()

        return Optional.ofNullable(latestStock)
    }
}
