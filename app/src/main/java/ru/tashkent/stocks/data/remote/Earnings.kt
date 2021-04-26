package ru.tashkent.stocks.data.remote

data class Earnings(
    val earningsChart: EarningsChart,
    val financialCurrency: String,
    val financialsChart: FinancialsChart,
    val maxAge: Int
)