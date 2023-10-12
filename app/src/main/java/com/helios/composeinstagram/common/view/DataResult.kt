package com.helios.composeinstagram.common.view

/**
 * A sealed class representing the result of a data operation, which can be in one of three states:
 *
 * 1. Loading: Represents that the data operation is currently in progress.
 * 2. Success: Represents a successful data operation with a result of type 'T'.
 * 3. Error: Represents a failed data operation with an associated 'Throwable' error.
 *
 * @param R The generic type parameter that represents the result of a successful operation.
 */
sealed class DataResult<out R> {

    object Loading : DataResult<Nothing>()

    data class Success<out T>(val data: T) : DataResult<T>()

    data class Error(val throwable: Throwable) : DataResult<Nothing>()
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