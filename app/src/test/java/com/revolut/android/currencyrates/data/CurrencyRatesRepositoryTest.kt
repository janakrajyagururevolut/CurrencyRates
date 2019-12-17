package com.revolut.android.currencyrates.data

import com.revolut.android.currencyrates.data.source.CurrencyRatesDataSource
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CurrencyRatesRepositoryTest {

    private lateinit var repository: CurrencyRatesRepository

    @Mock
    private lateinit var dataSource: CurrencyRatesDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = CurrencyRatesRepository(dataSource)
    }

    @Test
    fun getCurrentRates() =
        runBlockingTest {
            repository.getCurrentRates(anyString())
            verify(dataSource).getCurrentRates(anyString())
        }
}

