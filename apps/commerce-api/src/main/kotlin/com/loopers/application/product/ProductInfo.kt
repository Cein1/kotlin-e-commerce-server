package com.loopers.application.product

import java.math.BigDecimal

class ProductInfo {
    data class ProductListItem(
        val id: Long,
        val refBrandId: Long,
        val brandName: String,
        val name: String,
        val price: BigDecimal,
        val thumbnail: String,
        val likesCount: Long,
    )

    data class ProductDetail(
        val id: Long,
        val refBrandId: Long,
        val brandName: String,
        val brandDescription: String,
        val name: String,
        val price: BigDecimal,
        val thumbnail: String,
        val likesCount: Long,
    )
}
