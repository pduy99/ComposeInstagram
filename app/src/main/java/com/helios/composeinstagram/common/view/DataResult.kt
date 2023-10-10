package com.helios.composeinstagram.common.view

sealed class DataResult<out R> {

    object Loading : DataResult<Nothing>()

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val throwable: Throwable) : DataResult<Throwable>()
}

inline fun <reified R> DataResult<R>.doIfSuccess(callback: (value: R) -> Unit) {
    if (this is DataResult.Success) {
        callback.invoke(this.data)
    }
}

inline fun <reified R> DataResult<R>.doIfError(callback: (value: Throwable) -> Unit) {
    if (this is DataResult.Error) {
        callback.invoke(this.throwable)
    }
}

inline fun <reified R> DataResult<R>.getDataIfSuccessOrNull(): R? {
    return if (this is DataResult.Success) {
        return this.data
    } else {
        null
    }
}