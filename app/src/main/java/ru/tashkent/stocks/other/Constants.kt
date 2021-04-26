package ru.tashkent.stocks.other

import ru.tashkent.stocks.BuildConfig

object Constants {

    const val PAGE_SIZE = 20
    const val BASE_URL = "https://apidojo-yahoo-finance-v1.p.rapidapi.com/market/v2/"
    const val API_KEY = BuildConfig.ApiKey
    const val RAPIDAPI_HOST = "apidojo-yahoo-finance-v1.p.rapidapi.com"
    val SYMBOLS = listOf(
        "AAPL",
        "GOOG",
        "MSFT",
        "TSLA",
        "EMC",
        "HPQ",
        "DELL",
        "WDC",
        "HPE",
        "NTAP",
        "CPQ",
        "SNDK",
        "SEG"
    )

    const val DATABASE_NAME = "stocks-db"
}