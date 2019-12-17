package com.revolut.android.currencyrates.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("currency_nation")
fun bindCurrencyNation(view: TextView, currencyName: String) {
    val resources = view.resources
    view.text = resources.getString(
        resources.getIdentifier(
            currencyName, "string",
            view.context.packageName
        )
    )
}

@BindingAdapter("currency_flag")
fun bindCurrencyFlag(view: ImageView, currencyName: String) {
    val resources = view.resources
    val context = view.context
    val name = if (currencyName.equals("TRY")) "trym" else currencyName
    view.setImageResource(
        resources.getIdentifier(
            name.toLowerCase(),
            "drawable",
            context.packageName
        )
    )
}