package com.loopers.domain.brand

class BrandService(
    private val brandRepository: BrandRepository,
) {
    fun getBrandsByIds(brandIds: Set<Long>): Map<Long, BrandModel> {
        return brandRepository.findByIds(brandIds)
    }

    fun getBrandsById(brandId: Long): BrandModel? {
        return brandRepository.findById(brandId)
    }
}
