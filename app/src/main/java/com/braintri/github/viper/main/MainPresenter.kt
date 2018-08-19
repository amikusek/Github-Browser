package com.braintri.github.viper.main

import com.braintri.github.util.extension.observeOnIo
import com.braintri.github.util.extension.observeOnMain
import com.braintri.github.util.extension.retrySubscribe
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter
import java.util.concurrent.TimeUnit

class MainPresenter : BaseRxPresenter<
        MainContract.View,
        MainContract.Interactor,
        MainContract.Routing>(),
        ViperPresenter<MainContract.View> {

    override fun attachView(attachingView: MainContract.View?) {
        super.attachView(attachingView)

        addSubscription(
                view!!
                        .onSearchQueryChanges
                        .observeOnMain()
                        .doOnNext { view!!.showLoading() }
                        .observeOnIo()
                        .flatMapSingle { interactor.getRepos(it, view!!.currentPage) }
                        .observeOnMain()
                        .retrySubscribe(
                                onNext = {
                                    if (it.isNotEmpty()) {
                                        view!!.setItemsOnList(it)
                                        view!!.showList()
                                    } else {
                                        view!!.showEmptyState()
                                    }
                                },
                                onError = {
                                    view!!.showError(it)
                                }))
        addSubscription(
                view!!.onLoadMoreEvents
                        .throttleFirst(200, TimeUnit.MILLISECONDS)
                        .observeOnIo()
                        .flatMapSingle { interactor.getRepos(it, view!!.currentPage) }
                        .observeOnMain()
                        .retrySubscribe(
                                onNext =
                                {
                                    view?.addToList(it)
                                },
                                onError =
                                {
                                    view?.showError(it)
                                }
                        ))
    }

    override fun createRouting() = MainRouting()

    override fun createInteractor() = MainInteractor()
}
