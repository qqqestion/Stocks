package ru.tashkent.stocks.ui.viewmodels

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import ru.tashkent.stocks.data.local.Stock
import ru.tashkent.stocks.data.remote.Result
import ru.tashkent.stocks.other.Resource
import ru.tashkent.stocks.other.UiResource
import ru.tashkent.stocks.repositories.StockRepository
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class StockViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _stocks = MutableLiveData<UiResource<List<Result>>>()
    val stocks: LiveData<UiResource<List<Result>>> = _stocks

    private val _favouriteStocks = MutableLiveData<UiResource<List<Result>>>()
    val favouriteStocks: LiveData<UiResource<List<Result>>> = _favouriteStocks

    fun getStocks() {
        _stocks.postValue(UiResource.Loading())
        viewModelScope.launch {
            when (val response = repository.getTickers()) {
                is Resource.Success -> {
                    _stocks.postValue(UiResource.Success(response.data))
                }
                is Resource.Error -> {
                    Timber.d(response.error)
                    _stocks.postValue(UiResource.Error(processError(response.error!!)))
                }
            }
        }
    }

    fun getFavouriteStocks() {
        _favouriteStocks.postValue(UiResource.Loading())
        viewModelScope.launch {
            when (val response = repository.getFavouriteStocks()) {
                is Resource.Success -> {
                    Timber.d("response favourite: $response")
                    _favouriteStocks.postValue(UiResource.Success(response.data))
                }
                is Resource.Error -> {
                    Timber.d(response.error)
                    _favouriteStocks.postValue(UiResource.Error(processError(response.error!!)))
                }
            }
        }
    }

    private fun processError(error: Throwable): String {
        return error.message ?: "Unknown error"
    }

    fun toggleFavourite(stock: Result) {
        val wasFavourite = stock.isFavourite
        stock.isFavourite = !stock.isFavourite
        viewModelScope.launch {
            if (wasFavourite) {
                repository.deleteSymbol(Stock(stock.symbol))
            } else {
                repository.insertSymbol(Stock(stock.symbol))
            }
            val msg = if (stock.isFavourite) {
                "Added"
            } else {
                "Removed"
            }
            Timber.d("${stock.symbol} $msg")
//            Timber.d("db data: ${repository.getFavouriteSymbols()}")
        }
    }
}