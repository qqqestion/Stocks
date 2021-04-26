package ru.tashkent.stocks.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.tashkent.stocks.api.StockApi
import ru.tashkent.stocks.data.local.StockDao
import ru.tashkent.stocks.data.local.StockDatabase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideStockDatabase(
        @ApplicationContext context: Context
    ): StockDatabase = StockDatabase.getInstance(context)

    @Provides
    fun provideStockDao(stockDatabase: StockDatabase): StockDao = stockDatabase.stockDao()

    @Provides
    fun provideStockApi(): StockApi = StockApi.create()
}