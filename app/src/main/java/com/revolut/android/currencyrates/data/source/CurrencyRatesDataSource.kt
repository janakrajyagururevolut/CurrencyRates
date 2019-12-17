package com.revolut.android.currencyrates.data.source

import com.revolut.android.currencyrates.data.model.CurrentRates

interface CurrencyRatesDataSource {

    suspend fun getCurrentRates(baseCurrency: String): Pair<CurrentRates?, String?>
}