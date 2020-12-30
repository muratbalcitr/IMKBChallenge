package com.murat.veripark.network.services

import com.murat.veripark.network.responses.HandShakeResponse
import com.murat.veripark.network.responses.StockRequest
import com.murat.veripark.network.responses.Stocks
import com.murat.veripark.data.DeviceInfo
import com.murat.veripark.network.responses.StockDetailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {
    @POST("/api/handshake/start")
    suspend fun handShake(
        @Body request: DeviceInfo
    ): HandShakeResponse

    @POST("/api/stocks/list")
    suspend fun stocks(
        @Body period: StockRequest
    ): Stocks

    @POST("/api/stocks/detail")
    suspend fun stockDetail(
        @Body id: HashMap<String,String>
    ): StockDetailResponse
}