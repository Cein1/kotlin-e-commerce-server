package com.loopers.domain.point

import com.loopers.domain.user.UserModel
import com.loopers.domain.user.UserService
import com.loopers.utils.DatabaseCleanUp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.test.AfterTest

@SpringBootTest
class PointServiceIntegrationTest @Autowired constructor(
    private val userService: UserService,
    private val pointService: PointService,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    /**
     * **통합 테스트**
     *
     * - [ ]  해당 ID 의 회원이 존재할 경우, 보유 포인트가 반환된다.
     * - [ ]  해당 ID 의 회원이 존재하지 않을 경우, null 이 반환된다.
     */
    @AfterTest
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }

    @DisplayName("포인트 조회")
    @Nested
    inner class GetBalance {
        @DisplayName("해당 ID 의 회원이 존재할 경우, 보유 포인트가 반환된다.")
        @Test
        fun returnsBalance_whenUserIdExist() {
            val userA = UserModel(
                userId = "userA",
                email = "userA@example.com",
                gender = UserModel.GenderResponse.F,
                birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )
            userService.signup(userA)

            val balance = pointService.getBalance(userA.userId)

            assertAll(
                { assertThat(balance).isNotNull },
                { assertThat(balance?.userId).isEqualTo(userA.userId) },
                { assertThat(balance?.balance).isNotNull() },
            )
        }

        @DisplayName("해당 ID 의 회원이 존재하지 않을 경우, null 이 반환된다.")
        @Test
        fun returnsNull_whenUserIdNotExist() {
            val userId = "userA"
            val balance = pointService.getBalance(userId)

            assertAll(
                { assertThat(balance).isNull() },
            )
        }
    }
}
