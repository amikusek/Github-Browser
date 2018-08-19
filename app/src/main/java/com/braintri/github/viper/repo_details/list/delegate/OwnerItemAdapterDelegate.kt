package com.braintri.github.viper.repo_details.list.delegate

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braintri.github.R
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.repo_details.list.aggregate.OWNER_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.aggregate.OwnerListItem
import com.braintri.github.viper.repo_details.list.view_holder.OwnerViewHolder
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate

class OwnerItemAdapterDelegate : AdapterDelegate<List<ListingItem>>() {

    override fun isForViewType(items: List<ListingItem>, position: Int) =
            items[position].type == OWNER_ITEM_TYPE

    override fun onCreateViewHolder(parent: ViewGroup) =
            OwnerViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.viewholder_owner, parent, false))

    override fun onBindViewHolder(items: List<ListingItem>, position: Int,
                                  holder: RecyclerView.ViewHolder,
                                  payloads: List<Any>) {
        (holder as? OwnerViewHolder)?.bind((items[position] as OwnerListItem))
    }
}
