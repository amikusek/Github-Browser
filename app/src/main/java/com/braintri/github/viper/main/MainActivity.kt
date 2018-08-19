package com.braintri.github.viper.main

import com.braintri.github.R
import com.mateuszkoslacz.moviper.base.view.activity.autoinject.passive.ViperAiPassiveActivity
import com.mateuszkoslacz.moviper.iface.presenter.ViperPresenter

class MainActivity : ViperAiPassiveActivity<MainContract.View>(), MainContract.View {

    override fun createPresenter() = MainPresenter()

    override fun getLayoutId() = R.layout.activity_main
}
