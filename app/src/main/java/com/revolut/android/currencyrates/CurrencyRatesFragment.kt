package com.revolut.android.currencyrates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.revolut.android.currencyrates.R
import com.revolut.android.currencyrates.adapters.CurrencyRatesListAdapter
import com.revolut.android.currencyrates.databinding.FragmentCurrencyRatesBinding
import com.revolut.android.currencyrates.utilities.InjectorUtils
import com.revolut.android.currencyrates.viewmodel.CurrencyRatesViewModel


class CurrencyRatesFragment : Fragment() {

    private lateinit var binding: FragmentCurrencyRatesBinding
    private lateinit var adapter: CurrencyRatesListAdapter

    private val viewModel: CurrencyRatesViewModel by viewModels {
        InjectorUtils.provideCurrencyRatesViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCurrencyRatesBinding.inflate(inflater, container, false)
        setLifeCycleOwner()
        initAdapter()
        updateViews(getString(R.string.loadingCurrencyList), true)
        return binding.root
    }

    private fun setLifeCycleOwner() {
        binding.lifecycleOwner = this
    }

    private fun initAdapter() {
        adapter = CurrencyRatesListAdapter(R.layout.list_item_currency_rate)
        binding.currencyRatesList.layoutManager = LinearLayoutManager(requireActivity())
        binding.currencyRatesList.adapter = adapter
        (binding.currencyRatesList.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
            false
    }

    private fun updateViews(message: String, isListEmpty: Boolean) {
        binding.isCurrencyListEmpty = isListEmpty
        binding.progressMessage = message
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // observe currencies updated from API call
        viewModel.currencyList.observe(viewLifecycleOwner) { currencies ->
            updateViews("Success!", false)
            adapter.update(currencies)
        }
        // observe API failure
        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            updateViews(errorMessage, true)
        }
        // observe base currency and update the same while making API call
        adapter.baseCurrency.observe(viewLifecycleOwner) {
            binding.currencyRatesList.scrollToPosition(0)
            viewModel.onBaseCurrencyChanged(it)
        }
    }
}