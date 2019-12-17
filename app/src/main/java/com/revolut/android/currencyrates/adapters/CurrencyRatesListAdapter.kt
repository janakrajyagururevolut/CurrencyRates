package com.revolut.android.currencyrates.adapters

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.revolut.android.base.adapters.SingleLayoutBaseAdapter
import com.revolut.android.currencyrates.domain.model.Currency
import com.revolut.android.currencyrates.utilities.CURRENCY_NA

class CurrencyRatesListAdapter(layoutId: Int) : SingleLayoutBaseAdapter(layoutId) {

    private val baseCurrencyName: MutableLiveData<String> = MutableLiveData()
    // this is observed to update the base currency while making API call
    val baseCurrency: LiveData<String> = baseCurrencyName
    // the data for adapter to bind to the list items
    private var currencyList: MutableList<Currency> = mutableListOf()

    override fun getItemForPosition(position: Int): Any {
        return currencyList.get(position)
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    fun update(currencies: List<Currency>) {
        val mutableCurrencyList = currencies as MutableList
        if (currencyList.isEmpty()) {
            currencyList = mutableCurrencyList
            currencyList.add(0, Currency(value = 100.0F))
            setNewCurrencyValues(mutableCurrencyList)
            notifyDataSetChanged()
        } else {
            setNewCurrencyValues(mutableCurrencyList)
            notifyItemRangeChanged(1, currencies.size)
        }
    }

    private fun setNewCurrencyValues(currencies: MutableList<Currency>) {
        val multiplier = currencyList.get(0).value
        currencyList.forEachIndexed { index, currency ->
            index.takeIf { it > 0 && it < currencies.size }?.also {
                val value = currencies.find { it.name == currency.name }?.rate
                value?.let { currency.value = multiplier * value }
            }
        }
    }

    private fun updateBaseCurrency(baseCurrency: Currency) {
        val position = currencyList.indexOf(baseCurrency);
        currencyList.removeAt(position)
        currencyList.add(0, baseCurrency)
        notifyItemMoved(position, 0)
    }

    override fun onListItemClicked(currency: Currency) {
        updateBaseCurrency(currency)
        baseCurrencyName.value = currency.name
    }

    override fun onCurrencyValueChanged(value: CharSequence, currency: Currency) {
        // the change in base currency will be considered for calculation of other currencies
        if (currencyList.indexOf(currency) == 0) {
            val newValue = if (value.toString().equals("")) 0.0F else value.toString().toFloat()
            val baseCurrency = currencyList.get(0)
            // this prevents crash at the time of scroll when Recyclerview is updating 0th element
            if (baseCurrency.value != newValue) {
                baseCurrency.value = newValue
                setNewCurrencyValues(currencyList)
                notifyItemRangeChanged(1, currencyList.size)
            }
            // this prevents API call when the base currency value is 0
            if (newValue == 0.0F) baseCurrencyName.value = CURRENCY_NA else baseCurrencyName.value =
                baseCurrency.name
        }
    }
}
