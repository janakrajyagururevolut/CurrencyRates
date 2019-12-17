package com.revolut.android.currencyrates.domain.interactors

interface BaseInteractor {

    suspend fun getCurrencyRates(timeMillis: Long)
}