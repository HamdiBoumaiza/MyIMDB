package com.hb.test.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val name: String = "",
    val imageUrl: String = ""
) : Parcelable
