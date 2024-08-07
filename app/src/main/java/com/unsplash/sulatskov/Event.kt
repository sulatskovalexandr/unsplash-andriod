package com.unsplash.sulatskov

sealed interface Event<T> {

    class Loading<T> : Event<T>
    data class Success<T>(val data: T) : Event<T>
    data class Error<T>(val errorCode: Int) : Event<T>

    companion object {
        fun <T> loading(): Loading<T> = Loading<T>()

        fun <T> success(data: T) = Success(data)

        fun <T> error(errorCode: Int = 0): Error<T> = Error(errorCode)
    }
}