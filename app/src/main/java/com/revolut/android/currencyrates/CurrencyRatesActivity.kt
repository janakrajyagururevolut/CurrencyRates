package com.revolut.android.currencyrates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.revolut.android.currencyrates.R
import com.revolut.android.currencyrates.databinding.ActivityMainBinding

class CurrencyRatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this,
            R.layout.activity_main
        )
    }
}