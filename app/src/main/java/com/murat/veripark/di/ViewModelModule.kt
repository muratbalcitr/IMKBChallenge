package com.murat.veripark.di

import com.murat.veripark.ui.prelogin.PreLoginViewModel
import com.murat.veripark.ui.prelogin.splash.SplashViewModel
import com.murat.veripark.ui.main.MainViewModel
import com.murat.veripark.ui.main.stockDetail.StockDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


@ExperimentalCoroutinesApi
val viewModelModule = module {
    viewModel { MainViewModel(get(),get()) }
    viewModel { SplashViewModel(get(), get()) }
    viewModel { PreLoginViewModel() }
    viewModel { StockDetailViewModel(get(),get()) }
 }
