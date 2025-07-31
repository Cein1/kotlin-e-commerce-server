package com.loopers.domain.brand

import com.loopers.domain.BaseEntity
import com.loopers.support.error.CoreException
import com.loopers.support.error.ErrorType
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "brands")
class BrandModel(
    val name: String,
    val description: String?,
    val thumbnail: String?,
) : BaseEntity() {
    init {
        if (name.isBlank()) throw CoreException(ErrorType.BAD_REQUEST, "이름은 비어있을 수 없습니다.")
    }
}
