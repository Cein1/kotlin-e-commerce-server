package com.loopers.interfaces.api

import com.loopers.interfaces.api.point.PointV1Dto
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
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PointV1ApiE2ETest @Autowired constructor(
    private val testRestTemplate: TestRestTemplate,
    private val databaseCleanUp: DatabaseCleanUp,
) {
    /**
     * **E2E 테스트**
     *
     * - [ ]  포인트 조회에 성공할 경우, 보유 포인트를 응답으로 반환한다.
     * - [ ]  `X-USER-ID` 헤더가 없을 경우, `400 Bad Request` 응답을 반환한다.
     */

    companion object {
        private const val ENDPOINT_POINT_GET = "/api/v1/points"
        private const val ENDPOINT_POINT_CHARGE = "/api/v1/points/charge"
    }

    @AfterEach
    fun tearDown() {
        databaseCleanUp.truncateAllTables()
    }

    @DisplayName("GET /api/v1/points")
    @Nested
    inner class GetBalance {
        @DisplayName("포인트 조회에 성공할 경우, 보유 포인트를 응답으로 반환한다.")
        @Test
        fun returnPointStatus_whenRetrievePointSuccess() {
            val userId = "userA"
            val headers = HttpHeaders().apply { set("X-USER-ID", userId) }

            val responseType = object : ParameterizedTypeReference<ApiResponse<PointV1Dto.PointStatusResponse>>() {}
            val pointStatusResponse = testRestTemplate.exchange(ENDPOINT_POINT_GET, HttpMethod.GET, HttpEntity<Any>(headers), responseType)

            assertAll(
                { assertThat(pointStatusResponse.statusCode).isEqualTo(HttpStatus.OK) },
                { assertThat(pointStatusResponse.body?.data?.userId).isEqualTo(userId) },
                { assertThat(pointStatusResponse.body?.data?.balance).isNotNull() },
            )
        }

        @DisplayName("`X-USER-ID` 헤더가 없을 경우, `400 Bad Request` 응답을 반환한다.")
        @Test
        fun returnBadRequest_whenHeaderIsNotProvided() {
            val headers = HttpHeaders().apply { }

            val responseType = object : ParameterizedTypeReference<ApiResponse<PointV1Dto.PointStatusResponse>>() {}
            val pointStatusResponse = testRestTemplate.exchange(ENDPOINT_POINT_GET, HttpMethod.GET, HttpEntity<Any>(headers), responseType)

            assertAll(
                { assertThat(pointStatusResponse.statusCode).isEqualTo(HttpStatus.BAD_REQUEST) },
            )
        }
    }

    /**
     * **E2E 테스트**
     *
     * - [ ]  존재하는 유저가 1000원을 충전할 경우, 충전된 보유 총량을 응답으로 반환한다.
     * - [ ]  존재하지 않는 유저로 요청할 경우, `404 Not Found` 응답을 반환한다.
     */
    @DisplayName("POST /api/v1/points/charge")
    @Nested
    inner class Charge {
        @DisplayName("존재하는 유저가 1000원을 충전할 경우, 충전된 보유 총량을 응답으로 반환한다.")
        @Test
        fun returnBalance_whenCharge1000() {
            val userId = "userA"
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("X-USER-ID", userId)
            }

            val requestDto = PointV1Dto.PointChargeRequest(amount = 1000)
            val responseType = object : ParameterizedTypeReference<ApiResponse<PointV1Dto.PointStatusResponse>>() {}
            val pointStatusResponse = testRestTemplate.exchange(ENDPOINT_POINT_CHARGE, HttpMethod.POST, HttpEntity<PointV1Dto.PointChargeRequest>(requestDto, headers), responseType)

            assertAll(
                { assertThat(pointStatusResponse.statusCode).isEqualTo(HttpStatus.OK) },
                { assertThat(pointStatusResponse.body?.data?.userId).isEqualTo(userId) },
                { assertThat(pointStatusResponse.body?.data?.balance).isEqualTo(1000.toULong()) },
            )
        }

        @DisplayName("존재하지 않는 유저로 요청할 경우, `404 Not Found` 응답을 반환한다.")
        @Test
        fun returnNotFound_whenUserNotExist() {
            val userId = "userB"
            val headers = HttpHeaders().apply {
                contentType = MediaType.APPLICATION_JSON
                set("X-USER-ID", userId)
            }

            val requestDto = PointV1Dto.PointChargeRequest(amount = 1000)
            val responseType = object : ParameterizedTypeReference<ApiResponse<PointV1Dto.PointStatusResponse>>() {}
            val pointStatusResponse = testRestTemplate.exchange(ENDPOINT_POINT_CHARGE, HttpMethod.POST, HttpEntity<PointV1Dto.PointChargeRequest>(requestDto, headers), responseType)

            assertAll(
                { assertThat(pointStatusResponse.statusCode).isEqualTo(HttpStatus.NOT_FOUND) },
            )
        }
    }
}
