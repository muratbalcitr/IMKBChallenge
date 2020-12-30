package com.murat.veripark.ui.prelogin.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.murat.veripark.core.BaseViewModel
import com.murat.veripark.data.DeviceInfo
import com.murat.veripark.data.Event
import com.murat.veripark.network.repositories.ApiRepository
import com.murat.veripark.network.utils.Result
import com.murat.veripark.utils.PreferenceManager
import kotlinx.coroutines.launch

class SplashViewModel(
    val preferenceManager: PreferenceManager,
    val apiRepository: ApiRepository
) : BaseViewModel() {

    private val _event = MutableLiveData<Event<SplashViewEvent>>()
    val event: LiveData<Event<SplashViewEvent>> = _event

    fun navigateToMain() =
        _event.postValue(Event(SplashViewEvent.NavigateToMain))

    fun handShake(deviceInfo: DeviceInfo) = viewModelScope.launch {
        val response = apiRepository.handShake(deviceInfo)
        when (response) {
            is Result.Success -> {
                preferenceManager.handShake = response.data
                _event.postValue(Event(SplashViewEvent.HandShakeDecoder(response.data)))

            }
            is Result.Error -> {
                handleException(response.exception)
            }
        }
    }
}
