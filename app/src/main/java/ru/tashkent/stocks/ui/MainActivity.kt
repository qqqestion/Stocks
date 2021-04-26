package ru.tashkent.stocks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.tashkent.stocks.R
import ru.tashkent.stocks.api.StockApi
import ru.tashkent.stocks.databinding.ActivityMainBinding
import ru.tashkent.stocks.other.Constants
import ru.tashkent.stocks.repositories.StockRepository
import ru.tashkent.stocks.ui.fragments.StocksFragment
import timber.log.Timber
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // d7f49f4c4bd1e7c75fdbbb350e5fa0eb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}