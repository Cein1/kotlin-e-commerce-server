package com.loopers.interfaces.api.point

import com.loopers.interfaces.api.ApiResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "Point V1 API", description = "포인트 API 입니다.")
interface PointV1ApiSpec {
    @Operation(summary = "포인트 조회")
    fun getStatus(@Parameter(name = "X-USER-ID", required = true)userId: String): ApiResponse<PointV1Dto.PointStatusResponse>
    fun charge(
        @Parameter(name = "X-USER-ID", required = true)userId: String,
        request: PointV1Dto.PointChargeRequest,
    ): ApiResponse<PointV1Dto.PointStatusResponse>
}
