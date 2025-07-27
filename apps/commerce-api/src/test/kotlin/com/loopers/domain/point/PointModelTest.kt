package com.loopers.domain.point

import com.loopers.interfaces.api.point.PointV1Dto
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class PointModelTest {
    @DisplayName("포인트 충전")
    @Nested
    inner class Charge {
        @DisplayName("0 이하의 정수로 포인트를 충전 시 실패한다.")
        @ParameterizedTest
        @ValueSource(
            longs = [
                0,
                -1000,
                -1000000000,
            ],
        )
        fun failToCharge_whenAmountLessThanZero(amount: Long) {
            val request = PointV1Dto.PointChargeRequest(amount)
            val exception = assertThrows<CoreException> { request.toPointInfo("userA", amount.toULong()) }
            assertAll(
                { assertThat(exception.errorType).isEqualTo(ErrorType.BAD_REQUEST) },
            )
        }
    }
}
