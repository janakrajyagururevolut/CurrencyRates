package com.revolut.android.currencyrates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.revolut.android.currencyrates.domain.interactors.GetCurrencyRates

class CurrencyRatesViewModelFactory(
    private val interactor: GetCurrencyRates
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return CurrencyRatesViewModel(interactor) as T
    }
}