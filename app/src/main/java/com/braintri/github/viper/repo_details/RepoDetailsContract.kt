package com.braintri.github.viper.repo_details

import android.app.Activity

import com.hannesdorfmann.mosby.mvp.MvpView
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting

interface RepoDetailsContract {

    interface View : MvpView

    interface Interactor : ViperRxInteractor

    interface Routing : ViperRxRouting<Activity>
}
