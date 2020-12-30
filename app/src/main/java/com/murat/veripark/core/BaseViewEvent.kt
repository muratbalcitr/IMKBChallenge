package com.murat.veripark.core


sealed class BaseViewEvent {
    object ForceLogout : BaseViewEvent()

    object ShowCommonNetworkError : BaseViewEvent()

    object ShowConnectivityError : BaseViewEvent()

    object ShowInternalServerError : BaseViewEvent()

    object ShowUserNotFoundError : BaseViewEvent()

    data class ShowCustomError(val type: String, val message: String) : BaseViewEvent()
}
