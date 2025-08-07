package com.loopers.domain.order

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "orderItem")
class OrderItemModel(
    @Column(name = "ref_order_id", nullable = false)
    val refOrderId: Long,
    val productName: String,
    val thumbnail: String,
    val price: BigDecimal,
    val quantity: Long,
    val amount: BigDecimal,
    val status: String,
) : BaseEntity() {
    init {
    }
}
