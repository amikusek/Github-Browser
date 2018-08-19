package com.braintri.github.viper.main

import android.app.Activity

import com.hannesdorfmann.mosby.mvp.MvpView
import com.mateuszkoslacz.moviper.iface.interactor.ViperRxInteractor
import com.mateuszkoslacz.moviper.iface.routing.ViperRxRouting

interface MainContract {

    interface View : MvpView

    interface Interactor : ViperRxInteractor

    interface Routing : ViperRxRouting<Activity>
}
