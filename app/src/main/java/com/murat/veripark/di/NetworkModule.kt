package com.murat.veripark.di

import com.murat.veripark.network.utils.NetworkClient
import com.murat.veripark.utils.PreferenceManager
import org.koin.core.module.Module
import org.koin.dsl.module

val networkModule: Module = module {
    single { NetworkClient.provideApiService(get()) }
    single { NetworkClient.provideClient(get<PreferenceManager>()) }
}
