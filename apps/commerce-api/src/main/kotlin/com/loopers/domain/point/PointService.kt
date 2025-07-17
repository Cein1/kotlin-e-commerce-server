package com.loopers.domain.point

import com.loopers.domain.user.UserRepository
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PointService(private val userRepository: UserRepository, val pointRepository: PointRepository) {
    @Transactional(readOnly = true)
    fun getBalance(userId: String): PointModel? {
        val user = userRepository.findByUserId(userId) ?: return null
        return PointModel(userId = userId, amount = 0, balance = 0u)
    }

    @Transactional(readOnly = false)
    fun chargeAmount(userId: String, amount: ULong): ULong {
        val user = userRepository.findByUserId(userId) ?: throw CoreException(ErrorType.BAD_REQUEST, "존재하지 않는 ID")
        val currentBalance = pointRepository.findByUserId(userId)

        val newBalance: ULong = if (currentBalance != null) {
            currentBalance.balance + amount
        } else {
            amount
        }

        return newBalance
    }
}
