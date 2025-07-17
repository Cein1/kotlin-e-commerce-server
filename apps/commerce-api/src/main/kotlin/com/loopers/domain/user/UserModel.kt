package com.loopers.domain.user

import com.loopers.domain.BaseEntity
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "users")
class UserModel(userId: String, email: String, gender: GenderResponse, birthDate: LocalDate) : BaseEntity() {
    var userId: String = userId
        protected set

    var email: String = email
        protected set

    var gender: GenderResponse = gender
        protected set

    var birthDate: LocalDate = birthDate
        protected set

    enum class GenderResponse {
        M,
        F,
    }

    init {
        val userIdRegex = "^[a-zA-Z0-9]{1,10}$".toRegex()
        val userIdPatternMatch = this.userId.matches(userIdRegex)
        if (!userIdPatternMatch) throw CoreException(ErrorType.BAD_REQUEST, "ID 형식이 올바르지 않습니다")

        val emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
        val emailPatternMatch = this.email.matches(emailRegex)
        if (!emailPatternMatch) throw CoreException(ErrorType.BAD_REQUEST, "이메일 형식이 올바르지 않습니다")
    }
}
