package com.revolut.android.base.adapters

abstract class SingleLayoutBaseAdapter(private val layoutId: Int) : BaseAdapter() {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}