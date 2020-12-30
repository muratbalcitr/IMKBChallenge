package com.murat.veripark.di

import com.murat.veripark.utils.PreferenceManager
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val utilsModule: Module = module {
    single { PreferenceManager(androidApplication()) }
}