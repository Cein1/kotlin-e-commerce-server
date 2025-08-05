package com.loopers.domain.brand

import java.util.Optional

interface BrandRepository {
    fun findById(id: Long): Optional<BrandModel>
    fun findByIds(ids: Set<Long>): Map<Long, BrandModel>
    fun existsById(id: Long): Boolean
    fun save(brand: BrandModel): BrandModel
}
