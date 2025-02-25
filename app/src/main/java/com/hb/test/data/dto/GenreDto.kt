package com.hb.test.data.dto

import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("name")
    val name: String? = ""
)
