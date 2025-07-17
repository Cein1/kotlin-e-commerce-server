package com.loopers.domain.point

import com.loopers.domain.user.UserRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PointService(private val userRepository: UserRepository, val pointRepository: PointRepository) {
    @Transactional(readOnly = true)
    fun getBalance(userId: String): PointModel? {
        val user = userRepository.findByUserId(userId) ?: return null
        return PointModel(userId = userId, amount = 0, balance = 0u)
    }
}
