package com.technology.android_mvvm.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import com.technology.android_mvvm.R
import com.technology.android_mvvm.common.extensions.toast
import com.technology.android_mvvm.data.remote.extensions.toApiErrorResponse
import com.technology.android_mvvm.data.remote.response.ApiErrorResponse
import com.technology.android_mvvm.ui.common.Loader
import com.technology.android_mvvm.utils.LoggerUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

abstract class BaseViewModel(
    private val applicationContext: Context
) : ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
    }

    @Inject
    lateinit var loader: Loader

    protected fun <T> Flow<T>.launchNetwork(
        silent: Boolean = false,
        errorHandle: (ApiErrorResponse) -> Unit = {},
    ): Flow<T> = this
        .onStart { if (!silent) loader.start() }
        .onCompletion { if (!silent) loader.stop() }
        .catch { e ->
            val errorResponse = e.toApiErrorResponse()
            handleNetworkError(errorResponse)
            LoggerUtils.d(TAG, e)
            errorHandle(errorResponse)
        }.flowOn(Dispatchers.IO)

    private fun handleNetworkError(err: ApiErrorResponse) {
        when (err.status) {
            ApiErrorResponse.Status.HTTP_BAD_GATEWAY,
            ApiErrorResponse.Status.REMOTE_CONNECTION_ERROR ->
                applicationContext.toast(applicationContext.getString(R.string.server_connection_error))

            ApiErrorResponse.Status.NETWORK_CONNECTION_ERROR ->
                applicationContext.toast(applicationContext.getString(R.string.no_internet_connection))

            ApiErrorResponse.Status.HTTP_UNAUTHORIZED,
            ApiErrorResponse.Status.HTTP_BAD_REQUEST,
            ApiErrorResponse.Status.HTTP_NOT_FOUND ->
                err.message.let { applicationContext.toast(it) }

            ApiErrorResponse.Status.HTTP_FORBIDDEN ->
                applicationContext.toast(applicationContext.getString(R.string.permission_not_available))

            ApiErrorResponse.Status.HTTP_INTERNAL_ERROR ->
                applicationContext.toast(applicationContext.getString(R.string.network_internal_error))

            ApiErrorResponse.Status.HTTP_UNAVAILABLE ->
                applicationContext.toast(applicationContext.getString(R.string.network_server_not_available))

            ApiErrorResponse.Status.UNKNOWN ->
                applicationContext.toast(applicationContext.getString(R.string.something_went_wrong))
        }
    }
}