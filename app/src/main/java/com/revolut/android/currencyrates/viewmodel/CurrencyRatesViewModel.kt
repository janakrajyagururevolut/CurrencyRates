package com.revolut.android.currencyrates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.revolut.android.currencyrates.domain.interactors.GetCurrencyRates
import kotlinx.coroutines.launch

class CurrencyRatesViewModel(private val interactor: GetCurrencyRates) : ViewModel() {

    init {
        viewModelScope.launch {
            interactor.getCurrencyRates(1000)
        }
    }

    val currencyList = interactor.currencyList

    val errorMessage = interactor.errorMessage

    fun onBaseCurrencyChanged(baseCurrency: String) {
        interactor.setBaseCurrency(baseCurrency)
    }
}