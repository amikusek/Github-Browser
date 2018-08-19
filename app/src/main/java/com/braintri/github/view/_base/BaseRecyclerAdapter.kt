package com.braintri.github.view._base

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager


abstract class BaseRecyclerAdapter<ItemType> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegates by lazy {
        AdapterDelegatesManager<List<ItemType>>().apply {
            addAdapterDelegates(this)
        }
    }

    abstract fun addAdapterDelegates(delegatesManager: AdapterDelegatesManager<List<ItemType>>)

    var listingItems: List<ItemType> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
            adapterDelegates.onBindViewHolder(listingItems, position, holder)

    override fun getItemCount() = listingItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            adapterDelegates.onCreateViewHolder(parent, viewType)

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) =
            adapterDelegates.onViewRecycled(holder)

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        adapterDelegates.onFailedToRecycleView(holder)
        return super.onFailedToRecycleView(holder)
    }

    override fun getItemViewType(position: Int) = adapterDelegates.getItemViewType(listingItems, position)
}
