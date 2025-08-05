package com.loopers.domain.point

import com.loopers.domain.user.UserRepository
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
class PointService(private val userRepository: UserRepository, val pointRepository: PointRepository) {
    fun chargeAmount(userId: Long, amount: BigDecimal): BigDecimal {
        val user = userRepository.findById(userId).orElse(null) ?: throw CoreException(ErrorType.BAD_REQUEST, "존재하지 않는 ID")
        val currentBalance = pointRepository.findByRefUserId(user.id).orElse(null)

        val newBalance: BigDecimal = if (currentBalance != null) {
            currentBalance.balance + amount
        } else {
            amount
        }

        return newBalance
    }

    fun getPointBalance(refUserId: Long): BigDecimal {
        return pointRepository.getPointLog(refUserId)?.balance ?: (BigDecimal.ZERO)
    }

    fun deductPoint(
        userId: Long,
        amount: BigDecimal,
    ): BigDecimal {
        val user = userRepository.findById(userId).orElse(null) ?: throw CoreException(ErrorType.BAD_REQUEST, "존재하지 않는 ID")
        val currentBalance = pointRepository.findByRefUserId(user.id).orElse(null)

        val newBalance: BigDecimal = if (currentBalance != null) {
            currentBalance.balance + amount
        } else {
            amount
        }

        return newBalance
    }
}
