package com.revolut.android.currencyrates.domain.model

import com.revolut.android.currencyrates.utilities.INITIAL_BASE_CURRENCY

data class Currency (
    val name: String = INITIAL_BASE_CURRENCY,
    val rate: Float = 1.0F,
    var value: Float = 1.0F
)