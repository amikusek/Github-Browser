package com.braintri.github.viper.repo_details

import android.support.v7.app.AppCompatActivity
import com.braintri.github.data.entity.RepoDetails
import com.hannesdorfmann.mosby.mvp.MvpView
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting
import io.reactivex.Observable
import io.reactivex.Single

interface RepoDetailsContract {

    interface View : MvpView {
        val repoName: String
        val ownerName: String
        val navigationBackButtonClicks: Observable<Unit>
        val urlClicksEvents: Observable<String>
        fun setDetailsData(details: RepoDetails)
        fun showLoading()
        fun showContent()
        fun showEmptyState()
        fun showError(throwable: Throwable)
    }

    interface Interactor : ViperRxInteractor {
        fun getRepoDetails(owner: String, repo: String): Single<RepoDetails>
    }

    interface Routing : ViperRxRouting<AppCompatActivity> {
        fun closeScreen()
        fun openWebsiteInBrowser(url: String)
    }
}
