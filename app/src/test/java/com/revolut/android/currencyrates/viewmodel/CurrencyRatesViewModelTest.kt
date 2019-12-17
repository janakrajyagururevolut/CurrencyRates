package com.revolut.android.currencyrates.viewmodel

import com.revolut.android.currencyrates.domain.interactors.GetCurrencyRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CurrencyRatesViewModelTest {

    private val testMainThread = newSingleThreadContext("Main thread")

    private lateinit var viewModel: CurrencyRatesViewModel

    @Mock
    private lateinit var interactor: GetCurrencyRates

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testMainThread)
    }

    @Test
    fun getCurrencyList() =
        runBlockingTest {
            viewModel = CurrencyRatesViewModel(interactor)
            verify(interactor).getCurrencyRates(1000)
        }

    @Test
    fun onBaseCurrencyChanged() =
        runBlockingTest {
            val baseCurrency = "baseCurrency"
            viewModel = CurrencyRatesViewModel(interactor)
            viewModel.onBaseCurrencyChanged(baseCurrency)
            verify(interactor).setBaseCurrency(baseCurrency)
        }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testMainThread.close()
    }
}