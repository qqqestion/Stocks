package ru.tashkent.stocks.api

import okhttp3.Interceptor
import okhttp3.Response
import ru.tashkent.stocks.other.Constants

/**
 * Interceptor for adding api key and host header in requests for Rapidapi API
 *
 */
class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        original = original.newBuilder()
            .header("x-rapidapi-key", Constants.API_KEY)
            .header("x-rapidapi-host", Constants.RAPIDAPI_HOST)
            .build()
        return chain.proceed(original)
    }
}