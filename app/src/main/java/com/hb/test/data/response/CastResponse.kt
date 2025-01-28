package com.hb.test.data.response

import com.google.gson.annotations.SerializedName
import com.hb.test.data.dto.CastDto

data class CastResponse(
    @SerializedName("id")
    val id: Int,
    @SerializedName("cast")
    val castDtoResult: List<CastDto>
)
