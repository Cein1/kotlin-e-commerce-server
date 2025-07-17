package com.loopers.interfaces.api.point

import com.loopers.interfaces.api.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/points")
class PointV1Controller : PointV1ApiSpec {
    @GetMapping
    override fun getStatus(
        @RequestHeader("X-USER-ID")
        userId: String,
    ): ApiResponse<PointV1Dto.PointStatusResponse> = ApiResponse.success(
        PointV1Dto.PointStatusResponse(userId = userId, balance = 10000u),
    )

    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(): ResponseEntity<ApiResponse<Any?>> {
        val errorCode = "HEADER_MISSING"
        val errorMessage = "헤더가 존재하지 않습니다"

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ApiResponse.fail(errorCode, errorMessage))
    }
}
