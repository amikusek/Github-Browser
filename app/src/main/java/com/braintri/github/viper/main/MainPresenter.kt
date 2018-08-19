package com.braintri.github.viper.main

import com.mateuszkoslacz.moviper.base.presenter.BaseRxPresenter
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter

class MainPresenter : BaseRxPresenter<MainContract.View, MainContract.Interactor, MainContract.Routing>(), ViperPresenter<MainContract.View> {

    override fun createRouting() = MainRouting()

    override fun createInteractor() = MainInteractor()
}
