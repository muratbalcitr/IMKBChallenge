package com.murat.veripark.network.utils

import retrofit2.HttpException

sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>() {
        val code: Int
            get() = (exception as? HttpException)?.code() ?: 0
    }

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null

val Result<*>.unAuthorized
    get() = (this is Result.Error) &&
            (exception as? HttpException)?.code() == 401
