package com.loopers.domain.user

import java.util.Optional

interface UserRepository {
    fun findById(id: Long): Optional<UserModel>
    fun findByUserId(userId: String): UserModel?
    fun save(user: UserModel): UserModel
}
