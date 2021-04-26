package ru.tashkent.stocks.data.remote

data class QuoteResponse(
    val error: Any,
    val result: List<Result>
)