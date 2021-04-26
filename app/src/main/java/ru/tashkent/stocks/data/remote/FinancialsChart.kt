package ru.tashkent.stocks.data.remote

data class FinancialsChart(
    val quarterly: List<QuarterlyX>,
    val yearly: List<Yearly>
)