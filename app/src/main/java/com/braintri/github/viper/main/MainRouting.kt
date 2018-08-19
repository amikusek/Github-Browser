package com.braintri.github.viper.main

import android.support.v7.app.AppCompatActivity
import com.braintri.github.data.entity.Repo
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting

class MainRouting : BaseRxRouting<AppCompatActivity>(), MainContract.Routing {

    override fun startRepoDetailsScreen(repo: Repo) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
