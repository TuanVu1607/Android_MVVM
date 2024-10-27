package com.technology.android_mvvm.data.remote.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import javax.net.ssl.HttpsURLConnection

data class ApiErrorResponse(
    val status: Status = Status.UNKNOWN,

    @SerializedName("statusCode")
    val statusCode: Int = -1,

    @SerializedName("message")
    val message: String = "Something went wrong"
) {
    @Keep
    enum class Status(val code: Int) {
        UNKNOWN(-100),
        REMOTE_CONNECTION_ERROR(-101),
        NETWORK_CONNECTION_ERROR(-102),
        HTTP_UNAUTHORIZED(HttpsURLConnection.HTTP_UNAUTHORIZED),
        HTTP_FORBIDDEN(HttpsURLConnection.HTTP_FORBIDDEN),
        HTTP_BAD_REQUEST(HttpsURLConnection.HTTP_BAD_REQUEST),
        HTTP_NOT_FOUND(HttpsURLConnection.HTTP_NOT_FOUND),
        HTTP_INTERNAL_ERROR(HttpsURLConnection.HTTP_INTERNAL_ERROR),
        HTTP_UNAVAILABLE(HttpsURLConnection.HTTP_UNAVAILABLE),
        HTTP_BAD_GATEWAY(HttpsURLConnection.HTTP_BAD_GATEWAY);

        companion object {
            private val codes = entries.toTypedArray()
            operator fun get(code: Int) = codes.firstOrNull { it.code == code } ?: UNKNOWN
        }
    }
}
