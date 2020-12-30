package com.murat.veripark.network.utils

import com.murat.veripark.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response


class AuthInterceptor(
    private val preferenceManager: PreferenceManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("accept", "application/json")
         builder.addHeader("X-VP-Authorization", "${preferenceManager.handShake?.authorization}")

        return chain.proceed(builder.build())
    }
}
