package com.loopers.infrastructure.user

import com.loopers.domain.user.UserModel
import com.loopers.domain.user.UserRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class UserRepositoryImpl(private val userJpaRepository: UserJpaRepository) : UserRepository {
    override fun findByUserId(userId: String): UserModel? = userJpaRepository.findByUserId(userId)
    override fun findById(id: Long): Optional<UserModel> = userJpaRepository.findById(id)
    override fun save(user: UserModel): UserModel = userJpaRepository.save(user)
}
