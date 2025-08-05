package com.loopers.domain.order

import com.loopers.domain.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "products")
class OrderModel(
    @Column(name = "ref_user_id", nullable = false)
    val refUserId: Long,
) : BaseEntity() {
    init {
    }
}
