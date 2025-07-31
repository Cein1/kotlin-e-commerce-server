package com.loopers.domain.like

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.ZonedDateTime

@Entity
@Table(name = "likes")
class LikeModel(
    @EmbeddedId
    val id: LikeId,
) {
    @Column(name = "created_at", nullable = false, updatable = false)
    lateinit var createdAt: ZonedDateTime
        protected set

    @Column(name = "updated_at", nullable = false)
    lateinit var updatedAt: ZonedDateTime
        protected set

    constructor(userId: Long, productId: Long) : this(LikeId(userId, productId))
}
