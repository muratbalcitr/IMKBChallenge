package com.murat.veripark.core

import android.app.Application
import com.murat.veripark.BuildConfig
import com.murat.veripark.di.networkModule
import com.murat.veripark.di.repositoryModule
import com.murat.veripark.di.utilsModule
import com.murat.veripark.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class VeriParkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()

    }

    private fun initKoin() {
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.DEBUG else Level.INFO)
            androidContext(this@VeriParkApplication)
            modules(
                listOf(
                    viewModelModule,
                    networkModule,
                    utilsModule,
                    repositoryModule
                )
            )
        }
    }
}
