package com.braintri.github.viper.main

import android.support.v7.app.AppCompatActivity
import com.braintri.github.data.entity.Repo
import com.hannesdorfmann.mosby.mvp.MvpView
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting
import io.reactivex.Observable
import io.reactivex.Single

interface MainContract {

    interface View : MvpView {
        var currentPage: Int
        var isPageLoading: Boolean
        var hasLoadedAllItems: Boolean
        val onLoadMoreEvents: Observable<String>
        val onSearchQueryChanges: Observable<String>
        val onRepoListItemClicksEvents: Observable<Repo>
        fun setItemsOnList(list: List<Repo>)
        fun addToList(list: List<Repo>)
        fun showLoading()
        fun showInitialState()
        fun showEmptyState()
        fun showList()
        fun showError(throwable: Throwable)
    }

    interface Interactor : ViperRxInteractor {
        fun getRepos(query: String, page: Int): Single<List<Repo>>
    }

    interface Routing : ViperRxRouting<AppCompatActivity> {
        fun startRepoDetailsScreen(repo: Repo)
    }
}
