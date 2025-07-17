package com.loopers.domain.point

import com.loopers.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "points")
class PointModel(userId: String, amount: Long, balance: ULong) : BaseEntity() {
    var userId: String = userId
        protected set

    var amount: Long = amount
        protected set

    var balance: ULong = balance
        protected set
}
