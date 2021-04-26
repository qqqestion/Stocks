package ru.tashkent.stocks.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.tashkent.stocks.api.StockApi
import ru.tashkent.stocks.data.local.Stock
import ru.tashkent.stocks.data.local.StockDao
import ru.tashkent.stocks.data.remote.Result
import ru.tashkent.stocks.data.remote.YahooResponse
import ru.tashkent.stocks.other.Resource
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository @Inject constructor(
    private val stockApi: StockApi,
    private val stockDao: StockDao
) {

    suspend fun getTickers(): Resource<List<Result>> = withContext(Dispatchers.IO) {
        val response: YahooResponse
        try {
            response = stockApi.getTickers()
        } catch (e: Throwable) {
            return@withContext Resource.Error(e)
        }
        val favouriteSymbols = stockDao.getAll()
        val stocks = response.quoteResponse.result.onEach { stock ->
            stock.isFavourite = favouriteSymbols.contains(stock.symbol)
        }
        Timber.d("yahoo stocks: $stocks")
        return@withContext Resource.Success(stocks)
    }

    suspend fun insertSymbol(stock: Stock) = withContext(Dispatchers.IO) {
        stockDao.insert(stock)
    }

    suspend fun deleteSymbol(stock: Stock) = withContext(Dispatchers.IO) {
        stockDao.delete(stock)
    }

    suspend fun getFavouriteStocks(): Resource<List<Result>> = withContext(Dispatchers.IO) {
        val favouriteSymbols = stockDao.getAll()
        val response: YahooResponse
        try {
            response = stockApi.getTickers(symbols = favouriteSymbols.joinToString(","))
        } catch (e: Throwable) {
            return@withContext Resource.Error(e)
        }
        Timber.d("favourite symbols: $favouriteSymbols")
        Timber.d("yahoo response for favourite symbols: $response")
        val stocks = response.quoteResponse.result.onEach {
            it.isFavourite = true
        }
        return@withContext Resource.Success(stocks)
    }
}