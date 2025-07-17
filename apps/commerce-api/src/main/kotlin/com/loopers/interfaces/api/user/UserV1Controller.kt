package com.loopers.interfaces.api.user

import com.loopers.domain.user.UserModel
import com.loopers.infrastructure.user.UserJpaRepository
import com.loopers.interfaces.api.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
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
}
