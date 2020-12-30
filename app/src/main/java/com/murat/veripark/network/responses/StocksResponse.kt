package com.murat.veripark.network.responses

import com.google.gson.annotations.SerializedName
import com.murat.veripark.ext.format

data class Stocks(
    @SerializedName("stocks")
    val stock:ArrayList<StocksResponse>
) {
    data class StocksResponse(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("isDown")
        val isDown: Boolean?,
        @SerializedName("isUp")
        val isUp: Boolean?,
        @SerializedName("bid")
        val bid: Float?,
        @SerializedName("difference")
        val difference: Float?,
        @SerializedName("offer")
        val offer: Float?,
        @SerializedName("price")
        val price: Float?,
        @SerializedName("volume")
        val volume: Double?,
        @SerializedName("symbol")
        var symbol: String?
    ) {
        val formattedVolume: String?
        get() = volume?.format(volume)
        data class Status(
            @SerializedName("isSuccess")
            val isSuccess: Boolean?,
            @SerializedName("error")
            val error: Error?
        ) {
            data class Error(
                @SerializedName("code")
                val code: Int,
                @SerializedName("message")
                val message: String
            )
        }
    }
}