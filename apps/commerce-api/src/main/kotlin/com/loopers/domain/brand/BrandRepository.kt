package com.loopers.domain.brand

interface BrandRepository {
    fun findById(id: Long): BrandModel?
    fun findByIds(ids: Set<Long>): Map<Long, BrandModel>
    fun existsById(id: Long): Boolean
    fun save(brand: BrandModel): BrandModel
}
