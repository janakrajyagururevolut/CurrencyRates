package com.revolut.android.currencyrates.domain.interactors

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.revolut.android.currencyrates.data.model.CurrentRates
import com.revolut.android.currencyrates.data.source.CurrencyRatesDataSource
import com.revolut.android.currencyrates.domain.mapper.CurrencyEntityMapper
import com.revolut.android.currencyrates.domain.model.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class GetCurrencyRatesTest {

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private val testMainThread = newSingleThreadContext("Main thread")

    private lateinit var interactor: GetCurrencyRates

    @Mock
    private lateinit var currencyEntityMapper: CurrencyEntityMapper

    @Mock
    private lateinit var dataSource: CurrencyRatesDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testMainThread)
        interactor = GetCurrencyRates(currencyEntityMapper, dataSource)
    }

    @Test
    fun getCurrencyRates() =
        runBlockingTest {
            val pair: Pair<CurrentRates?, String?> = mockClass()
            val currencyRates = mock(CurrentRates::class.java)
            val rates: Map<String, Float>? = HashMap()
            `when`(currencyRates.rates).thenReturn(rates)
            `when`(pair.first).thenReturn(currencyRates)
            `when`(dataSource.getCurrentRates(anyString())).thenReturn(pair)
            interactor.getLatestRates()
            verify(currencyEntityMapper).apply(currencyRates)
        }

    @Test
    fun getCurrencyList() =
        runBlockingTest {
            val pair: Pair<CurrentRates?, String?> = mockClass()
            val currencyRates = mock(CurrentRates::class.java)
            val rates: Map<String, Float>? = HashMap()
            `when`(currencyRates.rates).thenReturn(rates)
            `when`(pair.first).thenReturn(currencyRates)
            `when`(dataSource.getCurrentRates(anyString())).thenReturn(pair)
            val currencies: List<Currency> = mockClass()
            `when`(currencyEntityMapper.apply(currencyRates)).thenReturn(currencies)
            interactor.getLatestRates()
            assertEquals(interactor.currencyList.value, currencies)
        }

    @Test
    fun getErrorMessage() =
        runBlockingTest {
            val pair: Pair<CurrentRates?, String?> = mockClass()
            `when`(pair.second).thenReturn("error")
            `when`(dataSource.getCurrentRates(anyString())).thenReturn(pair)
            interactor.getLatestRates()
            assertEquals(interactor.errorMessage.value, "error")
        }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testMainThread.close()
    }

    inline fun <reified T : Any> mockClass() = mock(T::class.java)
}