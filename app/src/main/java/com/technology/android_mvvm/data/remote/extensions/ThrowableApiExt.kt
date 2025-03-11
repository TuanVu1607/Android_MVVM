package com.technology.android_mvvm.data.remote.extensions

import com.google.gson.JsonSyntaxException
import com.squareup.moshi.JsonDataException
import com.technology.android_mvvm.data.remote.providers.MoshiBuilderProvider
import com.technology.android_mvvm.data.remote.response.ApiErrorResponse
import com.technology.android_mvvm.utils.LoggerUtils
import com.technology.android_mvvm.utils.NoConnectivityException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException

const val THROWABLE_API_ERROR_TAG = "THROWABLE_API_ERROR"

fun Throwable.toApiErrorResponse(): ApiErrorResponse {
    val defaultResponse = ApiErrorResponse()
    try {
        return when (this) {
            is ConnectException ->
                return ApiErrorResponse(ApiErrorResponse.Status.REMOTE_CONNECTION_ERROR, 0)

            is NoConnectivityException ->
                return ApiErrorResponse(ApiErrorResponse.Status.NETWORK_CONNECTION_ERROR, 0)

            is HttpException -> {
                val error = parseErrorResponse(response())

                if (error != null)
                    ApiErrorResponse(
                        ApiErrorResponse.Status[code()],
                        error.statusCode,
                        error.message
                    )
                else
                    defaultResponse
            }

            else -> {
                val message = this.message
                if (message != null && message.contains("unexpected end of stream"))
                    return ApiErrorResponse(ApiErrorResponse.Status.REMOTE_CONNECTION_ERROR, 0)
                return defaultResponse
            }
        }
    } catch (e: IOException) {
        LoggerUtils.e(THROWABLE_API_ERROR_TAG, e.toString())
    } catch (e: JsonSyntaxException) {
        LoggerUtils.e(THROWABLE_API_ERROR_TAG, e.toString())
    } catch (e: NullPointerException) {
        LoggerUtils.e(THROWABLE_API_ERROR_TAG, e.toString())
    }
    return defaultResponse
}

private fun parseErrorResponse(response: Response<*>?): ApiErrorResponse? {
    val jsonString = response?.errorBody()?.string()
    return try {
        val moshi = MoshiBuilderProvider.moshiBuilder.build()
        val adapter = moshi.adapter(ApiErrorResponse::class.java)
        adapter.fromJson(jsonString.orEmpty())
    } catch (exception: IOException) {
        null
    } catch (exception: JsonDataException) {
        null
    }
}