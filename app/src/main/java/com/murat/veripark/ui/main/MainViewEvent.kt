package com.murat.veripark.ui.main

import com.murat.veripark.network.responses.Stocks

sealed class MainViewEvent {
    data class NavigateToDetail(val item: Stocks.StocksResponse) : MainViewEvent()
}