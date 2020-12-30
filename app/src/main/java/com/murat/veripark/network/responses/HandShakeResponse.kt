package com.murat.veripark.network.responses

import com.google.gson.annotations.SerializedName

data class HandShakeResponse(
    @SerializedName("aesKey")
    val aesKey:String,
    @SerializedName("aesIV")
    val aesIV:String,
    @SerializedName("authorization")
    val authorization:String,
    @SerializedName("lifeTime")
    val lifeTime:String,
    @SerializedName("status")
    val status: Status

){
    data class Status(
        @SerializedName("isSuccess")
        val isSuccess :Boolean,
        @SerializedName("error")
        val error : Error
    ){
        data class Error(
            @SerializedName("code")
            val code:Int,
            @SerializedName("message")
            val message:String
        )
    }
}