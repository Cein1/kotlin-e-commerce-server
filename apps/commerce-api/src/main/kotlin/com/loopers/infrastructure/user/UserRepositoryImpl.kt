package com.loopers.infrastructure.user

import com.loopers.domain.user.UserModel
import com.loopers.domain.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository) : UserRepository {
    override fun findByUserId(userId: String): UserModel? = userJpaRepository.findByUserId(userId)
    override fun save(user: UserModel): UserModel = userJpaRepository.save(user)
}
