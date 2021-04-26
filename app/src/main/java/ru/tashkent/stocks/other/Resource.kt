package ru.tashkent.stocks.other

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {

    class Success<T>(data: T? = null) : Resource<T>(data)
    class Loading<T>() : Resource<T>()
    class Error<T>(error: Throwable? = null) : Resource<T>(error = error)
}
