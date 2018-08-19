package com.braintri.github.viper.main.list.delegate

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.braintri.github.R
import com.braintri.github.data.entity.Repo
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.main.list.aggregate.REPO_ITEM_TYPE
import com.braintri.github.viper.main.list.aggregate.RepoListItem
import com.braintri.github.viper.main.list.view_holder.RepoViewHolder
import com.hannesdorfmann.adapterdelegates3.AdapterDelegate
import io.reactivex.subjects.PublishSubject

class RepoListAdapterDelegate(private val itemClicks: PublishSubject<Repo>) : AdapterDelegate<List<ListingItem>>() {

    override fun isForViewType(items: List<ListingItem>, position: Int) =
            items[position].type == REPO_ITEM_TYPE

    override fun onCreateViewHolder(parent: ViewGroup) =
            RepoViewHolder(LayoutInflater
                    .from(parent.context)
                    .inflate(R.layout.viewholder_repository, parent, false),
                    itemClicks)

    override fun onBindViewHolder(items: List<ListingItem>, position: Int,
                                  holder: RecyclerView.ViewHolder,
                                  payloads: List<Any>) {
        (holder as? RepoViewHolder)?.bind((items[position] as RepoListItem))
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        (holder as RepoViewHolder).unbind()
        super.onViewDetachedFromWindow(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        (holder as RepoViewHolder).unbind()
        return super.onFailedToRecycleView(holder)
    }
}
