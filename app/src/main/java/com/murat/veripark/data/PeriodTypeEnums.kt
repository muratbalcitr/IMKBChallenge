package com.murat.veripark.data


enum class PeriodTypeEnum(val period: String) {
    PERIOD_ALL("all"),
    PERIOD_INCREASING("increasing"),
    PERIOD_DECREASING("decreasing"),
    PERIOD_VOLUME30("volume30"),
    PERIOD_VOLUME50("volume50"),
    PERIOD_VOLUME100("volume100");
}