package com.murat.veripark.di

import com.murat.veripark.network.repositories.ApiRepository
import com.murat.veripark.network.repositories.DefaultApiRepository
import org.koin.core.module.Module
import org.koin.dsl.module

val repositoryModule: Module = module {
    single<ApiRepository> { DefaultApiRepository(get(), get()) }
}
