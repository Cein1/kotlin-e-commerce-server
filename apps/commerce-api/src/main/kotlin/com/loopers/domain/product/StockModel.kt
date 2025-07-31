package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "stocks")
class StockModel(
    @Column(
        name = "ref_product_id",
        nullable = false,
    )
    val refProductId: Long,
    val inbound: Long?,
    val outbound: Long?,
    val available: Long,
) : BaseEntity() {
    init {
        if (inbound != null && inbound <= 0) {
            throw CoreException(
                ErrorType.BAD_REQUEST,
                "입고 수량은 0보다 커야 합니다.",
            )
        }
        if (outbound != null && outbound <= 0) {
            throw CoreException(
                ErrorType.BAD_REQUEST,
                "출고 수량은 0보다 커야 합니다.",
            )
        }
        if (available < 0) {
            throw CoreException(
                ErrorType.BAD_REQUEST,
                "가용 재고는 음수일 수 없습니다.",
            )
        }

        val isOnlyInboundNotNull = inbound != null && outbound == null
        val isOnlyOutboundNotNull = inbound == null && outbound != null

        if (!(isOnlyInboundNotNull || !isOnlyOutboundNotNull)) {
            throw CoreException(
                ErrorType.BAD_REQUEST,
                "재고 기록시 입고와 출고 중 하나만 값이 있어야 합니다. 둘 다 NULL 이거나, 둘 다 값이 있으면 안됩니다.",
            )
        }
    }
}
