package com.loopers.interfaces.api.user

import com.loopers.interfaces.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "User V1 API", description = "Loopers 예시 API 입니다.")
interface UserV1ApiSpec {
    @Operation(summary = "회원가입")
    fun signup(signupRequest: UserV1Dto.SignupRequest): ApiResponse<UserV1Dto.UserResponse>

    @Operation(summary = "사용자 정보 조회")
    fun getUserInfo(@Parameter(name = "X-USER-ID", required = true)userId: String): ApiResponse<UserV1Dto.UserResponse>
}
