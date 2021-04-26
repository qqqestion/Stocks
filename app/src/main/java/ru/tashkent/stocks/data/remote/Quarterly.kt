package ru.tashkent.stocks.data.remote

data class Quarterly(
    val `actual`: Double,
    val date: String,
    val estimate: Double
)