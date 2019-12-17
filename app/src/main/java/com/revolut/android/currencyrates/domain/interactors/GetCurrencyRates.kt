package com.revolut.android.currencyrates.domain.interactors

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revolut.android.currencyrates.data.model.CurrentRates
import com.revolut.android.currencyrates.data.source.CurrencyRatesDataSource
import com.revolut.android.currencyrates.domain.mapper.CurrencyEntityMapper
import com.revolut.android.currencyrates.domain.model.Currency
import com.revolut.android.currencyrates.utilities.CURRENCY_NA
import com.revolut.android.currencyrates.utilities.INITIAL_BASE_CURRENCY
import kotlinx.coroutines.delay

class GetCurrencyRates(
    private val currencyEntityMapper: CurrencyEntityMapper,
    private val dataSource: CurrencyRatesDataSource

) : BaseInteractor {

    private var baseCurrency: String = INITIAL_BASE_CURRENCY

    private val currentRates: MutableLiveData<List<Currency>> = MutableLiveData()
    val currencyList: LiveData<List<Currency>> = currentRates

    private val _errorMessage: MutableLiveData<String> = MutableLiveData()
    val errorMessage: LiveData<String> = _errorMessage

    override suspend fun getCurrencyRates(timeMillis: Long) {
        while (true) {
            if (baseCurrency != CURRENCY_NA) getLatestRates()
            delay(timeMillis)
        }
    }

    internal suspend fun getLatestRates() {
        val pair = dataSource.getCurrentRates(baseCurrency)
        pair.first?.let { mapCurrentRatesToCurrencyList(it) }
        pair.second?.let { _errorMessage.value = it }
    }

    private fun mapCurrentRatesToCurrencyList(currentRates: CurrentRates) {
        this.currentRates.value = currencyEntityMapper.apply(currentRates)
    }

    fun setBaseCurrency(baseCurrency: String) {
        this.baseCurrency = baseCurrency
    }
}