package com.braintri.github.viper.repo_details.list.delegate

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braintri.github.R
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.repo_details.list.aggregate.URL_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.aggregate.UrlListItem
import com.braintri.github.viper.repo_details.list.view_holder.UrlViewHolder
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import io.reactivex.subjects.PublishSubject

class UrlItemAdapterDelegate(private val urlClicksEvents: PublishSubject<String>) : AdapterDelegate<List<ListingItem>>() {

    override fun isForViewType(items: List<ListingItem>, position: Int) =
            items[position].type == URL_ITEM_TYPE

    override fun onCreateViewHolder(parent: ViewGroup) =
            UrlViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.viewholder_url, parent, false),
                    urlClicksEvents)

    override fun onBindViewHolder(items: List<ListingItem>, position: Int,
                                  holder: RecyclerView.ViewHolder,
                                  payloads: List<Any>) {
        (holder as? UrlViewHolder)?.bind((items[position] as UrlListItem))
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        (holder as UrlViewHolder).unbind()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        (holder as UrlViewHolder).unbind()
        return super.onFailedToRecycleView(holder)
    }
}
