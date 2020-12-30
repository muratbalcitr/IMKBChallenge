package com.murat.veripark.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.murat.veripark.core.BaseViewModel
import com.murat.veripark.data.Event
import com.murat.veripark.network.repositories.ApiRepository
import com.murat.veripark.network.responses.StockRequest
import com.murat.veripark.network.responses.Stocks
import com.murat.veripark.network.utils.Result
import com.murat.veripark.utils.AESUtil
import com.murat.veripark.utils.PreferenceManager
import kotlinx.coroutines.launch


class MainViewModel(
    val apiRepository: ApiRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val aesUtil = MutableLiveData<AESUtil>()
    private val _event = MutableLiveData<Event<MainViewEvent>>()
    val event: LiveData<Event<MainViewEvent>>
        get() = _event

    val _stocks = MutableLiveData<Stocks>()
    val stocks: LiveData<Stocks>
        get() = _stocks


    fun getCurrentStocksList(periodType: StockRequest?) = viewModelScope.launch {
        setLoading(true)
        val response = periodType?.let { apiRepository.stock(it) }
        when (response) {
            is Result.Success -> {
                _stocks.postValue(response.data)
            }
            is Result.Error -> {
                print("error" + response.exception.message!!)
            }
        }
    }

    fun handleItemClick(item: Stocks.StocksResponse) {
        _event.postValue(Event(MainViewEvent.NavigateToDetail(item)))
    }

}
