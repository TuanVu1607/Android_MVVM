package com.technology.android_mvvm.ui.base

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.technology.android_mvvm.R
import com.technology.android_mvvm.data.remote.extensions.toApiErrorResponse
import com.technology.android_mvvm.common.extensions.toast
import com.technology.android_mvvm.common.process.Loader
import com.technology.android_mvvm.data.remote.response.ApiErrorResponse
import com.technology.android_mvvm.utils.LoggerUtils
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class BaseViewModel(
    @ApplicationContext
    private val applicationContext: Context,
    private val loader: Loader
) : ViewModel() {

    companion object {
        const val TAG = "BaseViewModel"
    }

    protected fun launchNetwork(
        silent: Boolean = false,
        error: (ApiErrorResponse) -> Unit = {},
        block: suspend CoroutineScope.() -> Unit
    ) {
        if (!silent) {
            loader.start()
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    if (e is CancellationException) return@launch
                    val errorResponse = e.toApiErrorResponse()
                    handleNetworkError(errorResponse)
                    error(errorResponse)
                    LoggerUtils.d(TAG, e)
                    //LoggerUtils.record(e)
                } finally {
                    loader.stop()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    block()
                } catch (e: Throwable) {
                    if (e is CancellationException) return@launch
                    val errorResponse = e.toApiErrorResponse()
                    error(errorResponse)
                    LoggerUtils.d(TAG, e)
                    //LoggerUtils.record(e)
                }
            }
        }
    }

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