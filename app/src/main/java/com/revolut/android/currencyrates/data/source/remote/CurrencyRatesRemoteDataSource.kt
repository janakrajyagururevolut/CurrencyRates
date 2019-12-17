package com.revolut.android.currencyrates.data.source.remote

import android.content.res.Resources
import com.revolut.android.currencyrates.R
import com.revolut.android.currencyrates.data.model.CurrentRates
import com.revolut.android.currencyrates.data.source.CurrencyRatesDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CurrencyRatesRemoteDataSource(retrofit: Retrofit, private val resources: Resources) : CurrencyRatesDataSource {

    private val webService by lazy {
        retrofit.create(WebService::class.java)
    }

    override suspend fun getCurrentRates(baseCurrency: String): Pair<CurrentRates?, String?> =
        withContext(Dispatchers.IO) {
            try {
                Pair(webService.getCurrentRates(baseCurrency), null)
            } catch (e: SocketTimeoutException) {
                Pair(null, resources.getString(R.string.socket_timeout_error))
            } catch (e: UnknownHostException) {
                Pair(null, resources.getString(R.string.network_error))
            } catch (e: HttpException) {
                Pair(null, resources.getString(R.string.server_error))
            } catch (e: Exception) {
                Pair(null, resources.getString(R.string.generic_error))
            }
        }
}