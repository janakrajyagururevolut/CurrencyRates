package com.revolut.android.currencyrates.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class CurrentRates (

    @Expose
    @SerializedName("base")
    val base: String? = null,

    @Expose
    @SerializedName("date")
    val date: String? = null,

    @Expose
    @SerializedName("rates")
    val rates: Map<String, Float>? = null
)