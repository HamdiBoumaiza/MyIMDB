package com.hb.test.utils

import android.content.Context
import android.widget.Toast

fun Context.showToast(message: String) {
    Toast
        .makeText(
            this,
            message,
            Toast.LENGTH_LONG,
        ).show()
}

fun String.trimTitle(maxLength: Int = 15) = if (this.length <= maxLength) this else {
    val textWithEllipsis = this.removeRange(startIndex = maxLength, endIndex = this.length)
    "$textWithEllipsis..."
}
