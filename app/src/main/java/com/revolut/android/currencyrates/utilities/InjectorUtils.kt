package com.revolut.android.currencyrates.utilities

import android.content.Context
import com.revolut.android.currencyrates.data.CurrencyRatesRepository
import com.revolut.android.currencyrates.data.source.remote.CurrencyRatesRemoteDataSource
import com.revolut.android.currencyrates.domain.interactors.GetCurrencyRates
import com.revolut.android.currencyrates.domain.mapper.CurrencyEntityMapper
import com.revolut.android.currencyrates.viewmodel.CurrencyRatesViewModelFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object InjectorUtils {

    private val currencyEntityMapper: CurrencyEntityMapper = CurrencyEntityMapper()

    private val httpClient: OkHttpClient = OkHttpClient.Builder()
        .readTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS)
        .connectTimeout(HTTP_TIME_OUT, TimeUnit.SECONDS).build()

    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    private fun getCurrencyRatesRepository(context: Context): CurrencyRatesRepository =
        CurrencyRatesRepository.getInstance(
            CurrencyRatesRemoteDataSource(retrofit, context.resources)
        )

    private fun getCurrencyRatesInteractor(context: Context): GetCurrencyRates = GetCurrencyRates(
        currencyEntityMapper,
        getCurrencyRatesRepository(context)
    )

    fun provideCurrencyRatesViewModelFactory(context: Context): CurrencyRatesViewModelFactory =
        CurrencyRatesViewModelFactory(getCurrencyRatesInteractor(context))
}