package com.loopers.domain.payment

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "payment")
class Payment(
    @Column(name = "ref_user_id", nullable = false)
    val refUserId: Long,
    @Column(name = "ref_order_id", nullable = false)
    val refOrderId: Long,
    val amount: BigDecimal,
    val status: String,
) : BaseEntity()
