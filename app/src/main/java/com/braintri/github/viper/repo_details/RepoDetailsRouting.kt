package com.braintri.github.viper.repo_details

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import com.mateuszkoslacz.moviper.base.routing.BaseRxRouting


class RepoDetailsRouting : BaseRxRouting<AppCompatActivity>(), RepoDetailsContract.Routing {

    override fun closeScreen() {
        relatedContext?.finish()
    }

    override fun openWebsiteInBrowser(url: String) {
        relatedContext?.let {
            startActivity(it, Intent(Intent.ACTION_VIEW, Uri.parse(url)), null)
        }
    }
}
