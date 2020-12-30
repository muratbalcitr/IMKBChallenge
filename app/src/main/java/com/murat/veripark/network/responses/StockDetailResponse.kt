package com.murat.veripark.network.responses

import com.google.gson.annotations.SerializedName

data class StockDetailResponse(
    @SerializedName("isDown")
    val isDown: Boolean?,

    @SerializedName("isUp")
    val isUp: Boolean?,

    @SerializedName("bid")
    val bid: Double?,

    @SerializedName("channge")
    val channge: Double?,

    @SerializedName("count")
    val count: Int? ,

    @SerializedName("difference")
    val difference: Double? ,

    @SerializedName("offer")
    val offer: Double? ,

    @SerializedName("highest")
    val highest: Double? ,

    @SerializedName("lowest")
    val lowest: Double? ,

    @SerializedName("maximum")
    val maximum: Double? ,

    @SerializedName("minimum")
    val minimum: Double? ,

    @SerializedName("price")
    val price: Double? ,

    @SerializedName("volume")
    val volume: Double? ,

    @SerializedName("symbol")
    val symbol: String? ,

    @SerializedName("graphicData")
    val graphicData: List<GraphicData>? ,

    @SerializedName("status")
    val status: Stocks.StocksResponse.Status?
) {
    data class GraphicData(
        @SerializedName("day")
        val day: Int,
        @SerializedName("value")
        val value: Double
    )
}