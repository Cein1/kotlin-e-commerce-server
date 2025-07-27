package com.loopers.interfaces.api.user

import com.loopers.application.user.UserInfo
import com.loopers.domain.user.UserModel
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.validation.constraints.NotNull
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

class UserV1Dto {
    data class UserResponse(val userId: String, val email: String, val gender: UserModel.GenderResponse, val birthDate: String) {
        companion object {
            fun from(
                info: UserInfo,
            ): UserResponse = UserResponse(
                userId = info.userId,
                email = info.email,
                gender = info.gender,
                birthDate = info.birthDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
            )
        }
    }

    data class SignupRequest(
        @NotNull
        val userId: String,
        @NotNull
        val email: String,
        @NotNull
        val gender: UserModel.GenderResponse?,
        @NotNull
        val birthDate: String,
    ) {

            fun toUserInfo(): UserInfo {
                if (gender == null) {
                    throw CoreException(ErrorType.BAD_REQUEST, "성별은 필수입니다.")
                }

                val birthDateRegex = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$".toRegex()
                val birthDatePatternMatch = birthDate.matches(birthDateRegex)
                if (!birthDatePatternMatch) {
                    throw CoreException(ErrorType.BAD_REQUEST, "생년월일 형식이 올바르지 않습니다")
                }

                val parsedLocalBirthDate = try {
                    LocalDate.parse(this.birthDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                } catch (e: DateTimeParseException) {
                    throw CoreException(ErrorType.BAD_REQUEST, "존재하지 않는 날짜입니다.")
                }

                return UserInfo(
                    userId = this.userId,
                    email = this.email,
                    gender = this.gender,
                    birthDate = parsedLocalBirthDate,
                )
            }
    }
}
