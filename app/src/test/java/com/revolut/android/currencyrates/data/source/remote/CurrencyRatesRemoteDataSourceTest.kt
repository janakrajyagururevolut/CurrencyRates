package com.revolut.android.currencyrates.data.source.remote

import android.content.res.Resources
import com.revolut.android.currencyrates.utilities.INITIAL_BASE_CURRENCY
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit

class CurrencyRatesRemoteDataSourceTest {

    private lateinit var dataSource: CurrencyRatesRemoteDataSource

    @Mock
    private lateinit var retrofit: Retrofit

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var webService: WebService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        dataSource = CurrencyRatesRemoteDataSource(retrofit, resources)
    }

    @Test
    fun getCurrentRates() {
        runBlocking {
            `when`(retrofit.create(WebService::class.java)).thenReturn(webService)
            dataSource.getCurrentRates(INITIAL_BASE_CURRENCY)
            verify(webService).getCurrentRates(INITIAL_BASE_CURRENCY)
        }
    }
}