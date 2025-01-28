package com.hb.test.utils

import com.hb.test.R
import com.hb.test.utils.network.NoNetworkException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.mapError(): Int {
    return when (this) {
        is NoNetworkException -> R.string.no_network_exception
        is IOException -> R.string.network_error_please_try_again_later
        is HttpException -> R.string.error_server_please_try_again
        else -> R.string.oops_something_went_wrong_please_try_again
    }
}
