package com.loopers.application.point

import com.loopers.domain.point.PointModel

data class PointInfo(val userId: String, val amount: Long, val balance: ULong) {
    companion object {
        fun from(
            model: PointModel,
        ): PointInfo = PointInfo(userId = model.userId, amount = model.amount, balance = model.balance)
    }
}
