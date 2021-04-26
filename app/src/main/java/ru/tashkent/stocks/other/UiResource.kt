package ru.tashkent.stocks.other

sealed class UiResource<T>(
    val data: T? = null,
    val errorMsg: String? = null
) {

    class Success<T>(data: T? = null) : UiResource<T>(data)
    class Loading<T>() : UiResource<T>()
    class Error<T>(error: String? = null) : UiResource<T>(errorMsg = error)
}