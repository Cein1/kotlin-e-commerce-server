package com.loopers.interfaces.api.user

import com.loopers.domain.user.UserModel
import com.loopers.infrastructure.user.UserJpaRepository
import com.loopers.interfaces.api.ApiResponse
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/users")
class UserV1Controller(private val userJpaRepository: UserJpaRepository) : UserV1ApiSpec {
    @PostMapping
    override fun signup(
        @RequestBody signupRequest: UserV1Dto.SignupRequest,
    ): ApiResponse<UserV1Dto.UserResponse> = ApiResponse.success(
        UserV1Dto.UserResponse("userA", "userA@example.com", UserModel.GenderResponse.F, "2020-01-01"),
    )

    @GetMapping("/me")
    override fun getUserInfo(
        @RequestHeader("X-USER-ID") userId: String,
    ): ApiResponse<UserV1Dto.UserResponse> = ApiResponse.success(
        if (userId == "userA") {
            UserV1Dto.UserResponse("userA", "userA@example.com", UserModel.GenderResponse.F, "2020-01-01")
        } else {
            throw CoreException(ErrorType.NOT_FOUND, "존재하지 않는 ID")
        },
    )
}
