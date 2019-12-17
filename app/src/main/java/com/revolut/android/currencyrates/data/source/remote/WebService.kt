package com.revolut.android.currencyrates.data.source.remote

import com.revolut.android.currencyrates.data.model.CurrentRates
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {

    @GET("latest")
    suspend fun getCurrentRates(@Query("base") baseCurrency: String): CurrentRates?
}