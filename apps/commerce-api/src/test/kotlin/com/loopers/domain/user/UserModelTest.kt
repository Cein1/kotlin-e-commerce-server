package com.loopers.domain.user

import com.loopers.interfaces.api.user.UserV1Dto
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class UserModelTest {
    /**
     * **단위 테스트**
     * - [ ]  ID 가 `영문 및 숫자 10자 이내` 형식에 맞지 않으면, User 객체 생성에 실패한다.
     * - [ ]  이메일이 `xx@yy.zz` 형식에 맞지 않으면, User 객체 생성에 실패한다.
     * - [ ]  생년월일이 `yyyy-MM-dd` 형식에 맞지 않으면, User 객체 생성에 실패한다.
     */
    @DisplayName("회원가입")
    @Nested
    inner class Create {
        @DisplayName("ID 가 `영문 및 숫자 10자 이내` 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @ParameterizedTest
        @ValueSource(
            strings = [
                "userAAAAAAA",
                "123456777890",
                "skfj2p34u2-9534-06",
                "*&^*&53",
                "가가가",
            ],
        )
        fun failToCreateUserModel_whenUserIdIsInvalid(userId: String) {
            // arrange: 테스트에 필요한 데이터, 객체, 환경 등
            // 조건에 맞지 않는 ID 형식을 준비해
            val email = "userA@example.com"
            val gender = UserModel.GenderResponse.M
            val birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            // act: 테스트 대상이 되는 기능/메서드/행동 실제로 실행
            // 1. 조건 검사를 시행해
            // 2. 예외를 터지게 해: User 객체가 생성될 때 예외가 터진다.
            val exception = assertThrows<CoreException> { UserModel(userId = userId, email = email, gender = gender, birthDate = birthDate) }

            // assert: 실행 결과가 기대한 대로 나왔는지
            assertAll(
                { assertThat(exception.errorType).isEqualTo(ErrorType.BAD_REQUEST) },
            )
        }

        @DisplayName("이메일이 `xx@yy.zz` 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @ParameterizedTest
        @ValueSource(
            strings = [
                "userA",
                "@example.com",
                "userA.example.com",
                "userA@example@com",
                "userA@example",
                "userA@@example.com",
            ],
        )
        fun failToCreateUserModel_whenEmailIsInvalid(email: String) {
            val userId = "userA"
            val gender = UserModel.GenderResponse.M
            val birthDate = LocalDate.parse("2020-01-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"))

            val exception = assertThrows<CoreException> { UserModel(userId = userId, email = email, gender = gender, birthDate = birthDate) }

            assertAll(
                { assertThat(exception.errorType).isEqualTo(ErrorType.BAD_REQUEST) },
            )
        }

        @DisplayName("생년월일이 `yyyy-MM-dd` 형식에 맞지 않으면, User 객체 생성에 실패한다.")
        @ParameterizedTest
        @ValueSource(
            strings = [
                "2020",
                "2020-01",
                "2020.01.01",
                "2020.1.1",
                "01-01",
                "2020.01-01",
                "20-01-01",
            ],
        )
        fun failToCreateUserModel_whenBirthDateIsInvalid(birthDate: String) {
            val request = UserV1Dto.SignupRequest(userId = "userA", email = "userA@example.com", gender = UserModel.GenderResponse.M, birthDate = birthDate)
            val exception = assertThrows<CoreException> { request.toUserInfo() }

            assertAll(
                { assertThat(exception.errorType).isEqualTo(ErrorType.BAD_REQUEST) },
            )
        }
    }
}
