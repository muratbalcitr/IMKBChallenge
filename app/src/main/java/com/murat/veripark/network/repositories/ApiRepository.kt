package com.murat.veripark.network.repositories

import androidx.annotation.VisibleForTesting
import com.murat.veripark.network.responses.HandShakeResponse
import com.murat.veripark.network.responses.StockRequest
import com.murat.veripark.network.responses.Stocks
import com.murat.veripark.data.DeviceInfo
import com.murat.veripark.network.responses.StockDetailResponse
import com.murat.veripark.network.services.ApiServices
import com.murat.veripark.network.utils.Result
import com.murat.veripark.utils.PreferenceManager

interface ApiRepository {
    suspend fun handShake(request: DeviceInfo): Result<HandShakeResponse>
    suspend fun stock(period: StockRequest): Result<Stocks>
    suspend fun stockDetail(id:HashMap<String,String>):Result<StockDetailResponse>
}

class DefaultApiRepository(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val service: ApiServices,
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal val preferenceManager: PreferenceManager
) : ApiRepository {
    override suspend fun handShake(request: DeviceInfo): Result<HandShakeResponse> {
        return try {
            Result.Success(service.handShake(request))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun stock(period: StockRequest): Result<Stocks> {
        return try {
            Result.Success(service.stocks(period))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }

    override suspend fun stockDetail(id: HashMap<String, String>): Result<StockDetailResponse> {
        return try {
            Result.Success(service.stockDetail(id))
        } catch (exception: Exception) {
            Result.Error(exception)
        }
    }


}
