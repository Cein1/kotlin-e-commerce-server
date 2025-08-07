package com.loopers.infrastructure.like

import com.loopers.domain.like.LikeId
import com.loopers.domain.like.LikeModel
import org.springframework.data.jpa.repository.JpaRepository

interface LikeJpaRepository : JpaRepository<LikeModel, LikeId>
