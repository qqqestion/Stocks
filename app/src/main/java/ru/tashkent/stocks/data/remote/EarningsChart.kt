package ru.tashkent.stocks.data.remote

data class EarningsChart(
    val currentQuarterEstimate: Double,
    val currentQuarterEstimateDate: String,
    val currentQuarterEstimateYear: Int,
    val earningsDate: List<Int>,
    val quarterly: List<Quarterly>
)