package com.murat.veripark.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.murat.veripark.data.ApiError
import com.murat.veripark.data.Event
import retrofit2.HttpException
import java.net.UnknownHostException


abstract class BaseViewModel : ViewModel() {

    private val _loading = MutableLiveData<Boolean>(false)
    val loading: LiveData<Boolean> = _loading

    private val _baseEvent = MutableLiveData<Event<BaseViewEvent>>()
    val baseEvent: LiveData<Event<BaseViewEvent>> = _baseEvent

    fun setLoading(loading: Boolean) = _loading.postValue(loading)

    private fun showCommonNetworkError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCommonNetworkError))

    private fun showConnectivityError() =
        _baseEvent.postValue(Event(BaseViewEvent.ShowConnectivityError))

    private fun showCustomError(type: String, message: String) =
        _baseEvent.postValue(Event(BaseViewEvent.ShowCustomError(type, message)))

    open fun handleException(e: Exception) {
        when (e) {
            is HttpException -> {
                try {
                    showCustomError(
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ApiError::class.java
                        ).type,
                        Gson().fromJson(
                            e.response()?.errorBody()?.string(),
                            ApiError::class.java
                        ).message
                    )
                } catch (exception: Exception) {
                    showCommonNetworkError()
                }
            }

            is JsonSyntaxException -> showCommonNetworkError()

            is UnknownHostException -> showConnectivityError()
        }
    }


}
