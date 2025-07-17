package com.loopers.interfaces.api.point

import com.loopers.application.point.PointInfo
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.validation.constraints.NotNull

class PointV1Dto {
    data class PointStatusResponse(
        @NotNull
        val userId: String,
        @NotNull
        val balance: ULong,
    ) {
        companion object {
            fun from(
                info: PointInfo,
            ): PointStatusResponse = PointStatusResponse(userId = info.userId, balance = info.balance)
        }
    }

    data class PointChargeRequest(val amount: Long) {
        fun toPointInfo(userId: String, balance: ULong): PointInfo {
            if (amount <= 0) {
                throw CoreException(ErrorType.BAD_REQUEST, "포인트 충전은 양수만 가능합니다")
            } else {
                return PointInfo(userId = userId, amount = amount, balance = balance + amount.toULong())
            }
        }
    }
}
