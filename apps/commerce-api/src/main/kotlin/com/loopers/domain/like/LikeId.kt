package com.loopers.domain.like

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class LikeId(
    @Column(name = "ref_user_id", nullable = false)
    val userId: Long,
    @Column(name = "ref_product_id", nullable = false)
    val productId: Long,
) : Serializable
