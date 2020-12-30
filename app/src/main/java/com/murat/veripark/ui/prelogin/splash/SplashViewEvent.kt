package com.murat.veripark.ui.prelogin.splash

import com.murat.veripark.network.responses.HandShakeResponse


sealed class SplashViewEvent {
    data class HandShakeDecoder(val data: HandShakeResponse) : SplashViewEvent()

    object NavigateToWelcome : SplashViewEvent()

    object NavigateToMain : SplashViewEvent()

    object NetworkError : SplashViewEvent()
}