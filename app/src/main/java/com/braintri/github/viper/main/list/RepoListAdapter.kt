package com.braintri.github.viper.main.list

import com.braintri.github.data.entity.Repo
import com.braintri.github.view._base.BaseRecyclerAdapter
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.main.list.aggregate.REPO_ITEM_TYPE
import com.braintri.github.viper.main.list.delegate.RepoListAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import io.reactivex.subjects.PublishSubject

class RepoListAdapter : BaseRecyclerAdapter<ListingItem>() {

    val itemClicks = PublishSubject.create<Repo>()!!

    override fun addAdapterDelegates(delegatesManager: AdapterDelegatesManager<List<ListingItem>>) {
        delegatesManager.addDelegate(REPO_ITEM_TYPE, RepoListAdapterDelegate(itemClicks))
    }
}
