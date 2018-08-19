package com.braintri.github.viper.repo_details

import com.braintri.github.R
import com.mateuszkoslacz.moviper.base.view.activity.autoinject.passive.ViperAiPassiveActivity

class RepoDetailsActivity : ViperAiPassiveActivity<RepoDetailsContract.View>(), RepoDetailsContract.View {

    override fun createPresenter() = RepoDetailsPresenter()

    override fun getLayoutId() = R.layout.activity_repo_details
}
