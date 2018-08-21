package com.braintri.github.viper.repo_details.list

import com.braintri.github.view._base.BaseRecyclerAdapter
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.repo_details.list.aggregate.DESCRIPTION_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.aggregate.OWNER_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.aggregate.PARAMETER_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.aggregate.URL_ITEM_TYPE
import com.braintri.github.viper.repo_details.list.delegate.DescriptionAdapterDelegate
import com.braintri.github.viper.repo_details.list.delegate.OwnerItemAdapterDelegate
import com.braintri.github.viper.repo_details.list.delegate.ParameterItemAdapterDelegate
import com.braintri.github.viper.repo_details.list.delegate.UrlItemAdapterDelegate
import com.hannesdorfmann.adapterdelegates3.AdapterDelegatesManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class DetailsAdapter : BaseRecyclerAdapter<ListingItem>() {

    private val _urlClicksEvents = PublishSubject.create<String>()!!
    val urlClicksEvents: Observable<String> = _urlClicksEvents

    override fun addAdapterDelegates(delegatesManager: AdapterDelegatesManager<List<ListingItem>>) {
        delegatesManager.addDelegate(OWNER_ITEM_TYPE, OwnerItemAdapterDelegate())
        delegatesManager.addDelegate(DESCRIPTION_ITEM_TYPE, DescriptionAdapterDelegate())
        delegatesManager.addDelegate(PARAMETER_ITEM_TYPE, ParameterItemAdapterDelegate())
        delegatesManager.addDelegate(URL_ITEM_TYPE, UrlItemAdapterDelegate(_urlClicksEvents))
    }
}
