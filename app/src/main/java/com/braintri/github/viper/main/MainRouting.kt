package com.braintri.github.viper.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.braintri.github.constants.REPO_NAME_ARGS
import com.braintri.github.constants.REPO_OWNER_ARGS
import com.braintri.github.data.entity.Repo
import com.braintri.github.viper.repo_details.RepoDetailsActivity
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting

class MainRouting : BaseRxRouting<AppCompatActivity>(), MainContract.Routing {

    override fun startRepoDetailsScreen(repo: Repo) {
        relatedContext?.let {
            it.startActivity(Intent(it, RepoDetailsActivity::class.java).apply {
                putExtra(REPO_OWNER_ARGS, repo.owner.login)
                putExtra(REPO_NAME_ARGS, repo.name)
            })
        }
    }
}
