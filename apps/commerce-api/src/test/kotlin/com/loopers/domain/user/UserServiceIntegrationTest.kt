package com.loopers.domain.user

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import com.loopers.utils.DatabaseCleanUp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.SpyBean
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@SpringBootTest
class UserServiceIntegrationTest @Autowired constructor(
    private val userService: UserService,
    @SpyBean
    private val userRepository: UserRepository,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    @AfterEach
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }

    @DisplayName("회원 가입")
    @Nested
    inner class Create {
        @DisplayName("회원 가입시 User 저장이 수행된다. ( spy 검증 )")
        @Test
        fun saveUserInfo_whenSignupExecuted() {
            // arrange
            val user = UserModel(
                userId = "userA",
                email = "userA@example.com",
                gender = UserModel.GenderResponse.F,
                birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )

            // act
            userService.signup(user)

            // assert
            verify(userRepository).save(any())
        }

        @DisplayName("이미 가입된 ID 로 회원가입 시도 시, 실패한다.")
        @Test
        fun failsToSignup_whenUserIdAlreadyExists() {
            // arrange
            val userA = UserModel(
                userId = "userA",
                email = "userA@example.com",
                gender = UserModel.GenderResponse.F,
                birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )

            // act
            userService.signup(userA)

            val userB = UserModel(
                userId = "userA",
                email = "userB@example.com",
                gender = UserModel.GenderResponse.M,
                birthDate = LocalDate.parse("2020-01-02", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )
            val exception = assertThrows<CoreException> { userService.signup(userB) }

            // assert
            assertAll(
                { assertThat(exception.errorType).isEqualTo(ErrorType.CONFLICT) },
            )
        }
    }

    @DisplayName("내 정보 조회")
    @Nested
    inner class GetUserInfo {
        @DisplayName("해당 ID 의 회원이 존재할 경우, 회원 정보가 반환된다.")
        @Test
        fun returnsUserInfo_whenUserIdExists() {
            val userModel = UserModel(
                userId = "userA",
                email = "userA@example.com",
                gender = UserModel.GenderResponse.F,
                birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )
            userRepository.save(userModel)

            val response = userService.getUserInfo(userId = "userA")

            // assert
            assertAll(
                { assertThat(response).isNotNull() },
                { assertThat(response?.userId).isEqualTo(userModel.userId) },
                { assertThat(response?.email).isEqualTo(userModel.email) },
                { assertThat(response?.gender).isEqualTo(userModel.gender) },
                { assertThat(response?.birthDate).isEqualTo(userModel.birthDate) },
            )
        }

        @DisplayName("해당 ID 의 회원이 존재하지 않을 경우, null 이 반환된다.")
        @Test
        fun returnsNull_whenUserIdNotExist() {
            userRepository.save(
                UserModel(
                    userId = "userA",
                    email = "userA@example.com",
                    gender = UserModel.GenderResponse.F,
                    birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                ),
            )

            val response = userService.getUserInfo(userId = "userB")

            // assert
            assertAll(
                { assertThat(response).isNull() },
            )
        }
    }
}
