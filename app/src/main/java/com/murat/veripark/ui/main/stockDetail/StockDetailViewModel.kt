package com.murat.veripark.ui.main.stockDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.murat.veripark.core.BaseViewModel
import com.murat.veripark.network.repositories.ApiRepository
import com.murat.veripark.network.responses.StockDetailResponse
import com.murat.veripark.network.utils.Result
import com.murat.veripark.utils.PreferenceManager
import kotlinx.coroutines.launch

class StockDetailViewModel(
    val apiRepository: ApiRepository,
    val preferenceManager: PreferenceManager
) : BaseViewModel() {

    val stockDetails = MutableLiveData<StockDetailResponse>()

    fun stockDetail(id: String) = viewModelScope.launch {
        val map = HashMap<String, String>()
        map["id"] = id
        val response = apiRepository.stockDetail(map)
        when (response) {
            is Result.Success -> {
                stockDetails.postValue(response.data)
            }
            is Result.Error -> {
                handleException(response.exception)
            }
        }
    }

}