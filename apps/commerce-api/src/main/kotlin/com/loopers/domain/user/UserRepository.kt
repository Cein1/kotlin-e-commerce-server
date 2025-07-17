package com.loopers.domain.user

interface UserRepository {
    fun findByUserId(userId: String): UserModel?
    fun save(user: UserModel): UserModel
}
