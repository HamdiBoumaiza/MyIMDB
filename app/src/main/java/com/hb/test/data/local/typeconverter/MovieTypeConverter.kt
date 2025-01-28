package com.hb.test.data.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hb.test.data.dto.GenreDto

class MovieTypeConverter {

    private val gson: Gson by lazy { Gson() }

    @TypeConverter
    fun fromGenreList(value: List<GenreDto>): String = gson.toJson(value)

    @TypeConverter
    fun toGenreList(value: String): List<GenreDto> =
        gson.fromJson(value, object : TypeToken<List<GenreDto>>() {}.type)
}
