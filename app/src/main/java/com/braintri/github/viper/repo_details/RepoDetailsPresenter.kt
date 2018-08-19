package com.braintri.github.viper.repo_details

import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter

class RepoDetailsPresenter : BaseRxPresenter<RepoDetailsContract.View, RepoDetailsContract.Interactor, RepoDetailsContract.Routing>(), ViperPresenter<RepoDetailsContract.View> {

    override fun createRouting() = RepoDetailsRouting()

    override fun createInteractor() = RepoDetailsInteractor()
}
