package com.loopers.domain.product

import com.loopers.domain.BaseEntity
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "products")
class ProductModel(
    @Column(name = "ref_brand_id", nullable = false)
    val refBrandId: Long,
    val name: String,
    val price: BigDecimal,
    val thumbnail: String,
) : BaseEntity() {
    init {
        if (name.isBlank()) throw CoreException(ErrorType.BAD_REQUEST, "상품 이름은 비어있을 수 없습니다.")
        if (price <= BigDecimal.ZERO) throw CoreException(ErrorType.BAD_REQUEST, "상품 가격은 0보다 커야 합니다..")
    }
}
