package com.murat.veripark.data

import com.google.gson.annotations.SerializedName

data class DeviceInfo(
    @SerializedName("deviceId")
    var deviceId: String,
    @SerializedName("systemVersion")
    val systemVersion: String,
    @SerializedName("platformName")
    val platformName: String,
    @SerializedName("deviceModel")
    val deviceModel: String,
    @SerializedName("manifacturer")
    val manifacturer: String
)
