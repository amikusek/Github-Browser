package com.braintri.github.viper.repo_details

import com.braintri.github.util.extension.observeOnIo
import com.braintri.github.util.extension.observeOnMain
import com.braintri.github.util.extension.retrySubscribe
import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter
import io.reactivex.subjects.PublishSubject

class RepoDetailsPresenter : BaseRxPresenter<RepoDetailsContract.View, RepoDetailsContract.Interactor, RepoDetailsContract.Routing>(), ViperPresenter<RepoDetailsContract.View> {

    private val loadDetailsSubject = PublishSubject.create<Any>()

    override fun attachView(attachingView: RepoDetailsContract.View?) {
        super.attachView(attachingView)

        addSubscription(
                loadDetailsSubject
                        .doOnNext { view!!.showLoading() }
                        .observeOnIo()
                        .flatMapSingle { interactor.getRepoDetails(view!!.ownerName, view!!.repoName) }
                        .observeOnMain()
                        .retrySubscribe(
                                onNext = {
                                    view!!.setDetailsData(it)
                                    view!!.showContent()
                                },
                                onError = {
                                    view!!.showEmptyState()
                                    view!!.showError(it)
                                }
                        ))
        addSubscription(
                view!!
                        .urlClicksEvents
                        .retrySubscribe(
                                onNext = { routing.openWebsiteInBrowser(it) },
                                onError = { it.printStackTrace() }))
        addSubscription(
                view!!
                        .navigationBackButtonClicks
                        .retrySubscribe(
                                onNext = { routing.closeScreen() },
                                onError = { it.printStackTrace() }))

        loadDetailsSubject.onNext(Unit)
    }

    override fun createRouting() = RepoDetailsRouting()

    override fun createInteractor() = RepoDetailsInteractor()
}
