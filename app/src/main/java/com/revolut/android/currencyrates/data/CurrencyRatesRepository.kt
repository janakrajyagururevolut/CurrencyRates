package com.revolut.android.currencyrates.data

import com.revolut.android.currencyrates.data.source.CurrencyRatesDataSource

class CurrencyRatesRepository internal constructor(
    private val dataSource: CurrencyRatesDataSource
) : CurrencyRatesDataSource {

    companion object {
        @Volatile
        private var instance: CurrencyRatesRepository? = null

        fun getInstance(dataSource: CurrencyRatesDataSource) =
            instance ?: synchronized(this) {
                instance ?: CurrencyRatesRepository(dataSource).also { instance = it }
            }
    }

    override suspend fun getCurrentRates(baseCurrency: String) =
        dataSource.getCurrentRates(baseCurrency)
}