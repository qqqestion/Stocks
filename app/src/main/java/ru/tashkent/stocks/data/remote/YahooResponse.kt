package ru.tashkent.stocks.data.remote

import com.google.gson.annotations.SerializedName

data class YahooResponse(
    @field:SerializedName("quoteResponse")
    val quoteResponse: QuoteResponse
)