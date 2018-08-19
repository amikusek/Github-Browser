package com.braintri.github.viper.main

import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.braintri.github.R
import com.braintri.github.data.entity.Repo
import com.braintri.github.util.extension.gone
import com.braintri.github.util.extension.visible
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.main.list.RepoListAdapter
import com.braintri.github.viper.main.list.aggregate.RepoListItem
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView
import com.mateuszkoslacz.moviper.base.view.activity.autoinject.passive.ViperAiPassiveActivity
import com.paginate.Paginate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : ViperAiPassiveActivity<MainContract.View>(), MainContract.View {

    private val adapter = RepoListAdapter()

    override var currentPage = 1
    override var isPageLoading = true
    override var hasLoadedAllItems = false
    override val onLoadMoreEvents = PublishSubject.create<String>()
    override val onSearchQueryChanges: Observable<String>
        get() = RxSearchView
                .queryTextChanges(searchView)
                .skipInitialValue()
                .filter { it.length > 3 }
                .debounce(500, TimeUnit.MILLISECONDS)
                .map { it.toString() }
    override val onRepoListItemClicksEvents: Observable<Repo>
        get() = adapter.itemClicks

    private var callbacks: Paginate.Callbacks = object : Paginate.Callbacks {
        override fun onLoadMore() {
            isPageLoading = true
            onLoadMoreEvents.onNext(searchView.query.toString())
        }

        override fun isLoading() = isPageLoading
        override fun hasLoadedAllItems() = hasLoadedAllItems
    }

    override fun injectViews() {
        super.injectViews()
        setReyclerView()
    }

    override fun setItemsOnList(list: List<Repo>) {
        isPageLoading = false
        currentPage += 1
        adapter.listingItems = list.map { RepoListItem(it) }
    }

    override fun addToList(list: List<Repo>) {
        isPageLoading = false
        currentPage += 1
        if (list.isEmpty())
            hasLoadedAllItems = true
        else {
            val adapterList: MutableList<ListingItem> = adapter.listingItems.toMutableList()
            list.forEach {
                adapterList.add(RepoListItem(it))
            }
            adapter.listingItems = adapterList
            adapter.notifyDataSetChanged()
        }
    }

    override fun showLoading() {
        loadingState.visible()
        emptyState.gone()
        initialState.gone()
        results.gone()
    }

    override fun showInitialState() {
        loadingState.gone()
        emptyState.gone()
        initialState.visible()
        results.gone()
    }

    override fun showEmptyState() {
        loadingState.gone()
        emptyState.visible()
        initialState.gone()
        results.gone()
    }

    override fun showList() {
        results.scrollToPosition(0)
        loadingState.gone()
        emptyState.gone()
        initialState.gone()
        results.visible()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show()
    }

    private fun setReyclerView() {
        results.adapter = adapter
        results.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        Paginate.with(results, callbacks)
                .addLoadingListItem(true)
                .build()
    }

    override fun createPresenter() = MainPresenter()

    override fun getLayoutId() = R.layout.activity_main
}
