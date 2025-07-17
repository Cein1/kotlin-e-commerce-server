package com.loopers.interfaces.api

import com.loopers.domain.user.UserModel
import com.loopers.infrastructure.user.UserJpaRepository
import com.loopers.interfaces.api.user.UserV1Dto
import com.loopers.utils.DatabaseCleanUp
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus

// https://docs.spring.io/spring-boot/api/kotlin/spring-boot-project/spring-boot-test/org.springframework.boot.test.web.client/-test-rest-template/index.html
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserV1ApiE2ETest @Autowired constructor(
    private val testRestTemplate: TestRestTemplate,
    private val userJpaRepository: UserJpaRepository,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    companion object {
        private const val ENDPOINT_SIGNUP: String = "/api/v1/users"
    }

    @AfterEach
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }

    @DisplayName("POST /api/v1/users")
    @Nested
    inner class Signup {
        @DisplayName("회원 가입이 성공할 경우, 생성된 유저 정보를 응답으로 반환한다.")
        @Test
        fun returnsUserInfo_whenSignupSuccess() {
            // arrange: 테스트에 필요한 데이터, 객체, 환경 등을 준비
            val signupRequest = UserV1Dto.SignupRequest("userA", "userA@example.com", UserModel.GenderResponse.F, "2020-01-01")
            val requestUrl = ENDPOINT_SIGNUP

            // act : 테스트 대상이 되는 기능/메서드/행동을 실제로 실행
            val responseType = object : ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>>() {}
            val response = testRestTemplate.exchange(requestUrl, HttpMethod.POST, HttpEntity<Any>(signupRequest), responseType)

            // assert : 실행 결과가 기대한 대로 나왔는지 확인
            // 회원가입은 ID 및 성별, 생년월일, 이메일 주소를 받습니다.
            assertAll(
                { assertThat(response.statusCode).isEqualTo(HttpStatus.OK) },
                { assertThat(response.body?.data?.userId).isEqualTo(signupRequest.userId) },
                { assertThat(response.body?.data?.gender).isEqualTo(signupRequest.gender) },
                { assertThat(response.body?.data?.birthDate).isEqualTo(signupRequest.birthDate) },
                { assertThat(response.body?.data?.email).isEqualTo(signupRequest.email) },
            )
        }

        @DisplayName("회원 가입 시에 성별이 없을 경우, 400 Bad Request 응답을 반환한다.")
        @Test
        fun throwsBadRequest_whenGenderIsNotProvided() {
            // arrange: 테스트에 필요한 데이터, 객체, 환경 등을 준비
//            val userModel = userJpaRepository.save(UserModel())
            val requestUrl = ENDPOINT_SIGNUP

            // act : 테스트 대상이 되는 기능/메서드/행동을 실제로 실행
            val responseType = object : ParameterizedTypeReference<ApiResponse<UserV1Dto.UserResponse>>() {}
            val response = testRestTemplate.exchange(requestUrl, HttpMethod.POST, HttpEntity<Any>(Unit), responseType)

            println("response.body.data: " + response.body?.data)

            // assert : 실행 결과가 기대한 대로 나왔는지 확인
            assertAll(
                { assertThat(response.body?.data?.gender).isNull() },
                { assertThat(response.body?.meta?.result).isEqualTo(ApiResponse.Metadata.Result.FAIL) },
                { assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST) },
            )
        }
    }
}
