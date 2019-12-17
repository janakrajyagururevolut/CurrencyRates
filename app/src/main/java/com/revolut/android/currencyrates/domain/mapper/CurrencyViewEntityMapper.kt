package com.revolut.android.currencyrates.domain.mapper

import androidx.arch.core.util.Function
import com.revolut.android.currencyrates.data.model.CurrentRates
import com.revolut.android.currencyrates.domain.model.Currency

class CurrencyEntityMapper : Function<CurrentRates, List<Currency>> {

    override fun apply(currencyRates: CurrentRates?): List<Currency> {
        val currencies: MutableList<Currency> = mutableListOf()
        currencyRates?.rates?.forEach {
            currencies.add(
                Currency(name = it.key, rate = it.value)
            )
        }
        return currencies
    }
}