package com.loopers.domain.user

import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class UserService(private val userRepository: UserRepository) {
    @Transactional(readOnly = false)
    fun signup(user: UserModel): UserModel {
            if (userRepository.findByUserId(user.userId) != null) {
                throw CoreException(errorType = ErrorType.CONFLICT, customMessage = "이미 존재하는 사용자입니다.")
            }

        return userRepository.save(user)
    }
}
