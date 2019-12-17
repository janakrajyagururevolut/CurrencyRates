package com.revolut.android.base.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.revolut.android.currencyrates.domain.model.Currency

abstract class BaseAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater, viewType, parent, false
        )
        return BaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val item = getItemForPosition(position)
        holder.bind(item, this)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    abstract fun onListItemClicked(currency: Currency)

    abstract fun onCurrencyValueChanged(value: CharSequence, currency: Currency)

    protected abstract fun getItemForPosition(position: Int): Any

    protected abstract fun getLayoutIdForPosition(position: Int): Int
}