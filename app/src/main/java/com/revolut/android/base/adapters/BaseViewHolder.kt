package com.revolut.android.base.adapters

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.revolut.android.currencyrates.BR

class BaseViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(currency: Any, listener: BaseAdapter) {
        binding.setVariable(BR.currency, currency)
        binding.setVariable(BR.handler, listener)
        binding.executePendingBindings()
    }
}