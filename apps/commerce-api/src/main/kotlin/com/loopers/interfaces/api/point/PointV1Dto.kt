package com.loopers.interfaces.api.point

import com.loopers.application.point.PointInfo
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
}
