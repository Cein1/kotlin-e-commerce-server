package com.loopers.application.user

import com.loopers.domain.user.UserModel
import java.time.LocalDate

data class UserInfo(val userId: String, val email: String, val gender: UserModel.GenderResponse, val birthDate: LocalDate) {
    companion object {
        fun from(
            model: UserModel,
        ): UserInfo = UserInfo(userId = model.userId, email = model.email, gender = model.gender, birthDate = model.birthDate)
    }
}
