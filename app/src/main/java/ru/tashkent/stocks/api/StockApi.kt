package ru.tashkent.stocks.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import ru.tashkent.stocks.BuildConfig
import ru.tashkent.stocks.data.remote.YahooResponse
import ru.tashkent.stocks.other.Constants.API_KEY
import ru.tashkent.stocks.other.Constants.BASE_URL
import ru.tashkent.stocks.other.Constants.RAPIDAPI_HOST
import ru.tashkent.stocks.other.Constants.SYMBOLS

interface StockApi {

    // TODO: добавить пагинацию
    @GET("get-quotes")
    suspend fun getTickers(
        @Query("symbols") symbols: String = SYMBOLS.joinToString(","),
        @Query("region") region: String = "US"
    ): YahooResponse

    companion object {
        fun create(): StockApi {
            val logger = HttpLoggingInterceptor().apply {
                level = Level.NONE
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .addInterceptor(ApiKeyInterceptor())
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(StockApi::class.java)
        }
    }
}
