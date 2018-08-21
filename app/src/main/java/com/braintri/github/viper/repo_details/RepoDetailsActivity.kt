package com.braintri.github.viper.repo_details

import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.braintri.github.R
import com.braintri.github.constants.REPO_NAME_ARGS
import com.braintri.github.constants.REPO_OWNER_ARGS
import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.util.extension.DATE_WITH_TIME_FORMAT
import com.braintri.github.util.extension.getFormattedString
import com.braintri.github.util.extension.gone
import com.braintri.github.util.extension.visible
import com.braintri.github.view._base.ListingItem
import com.braintri.github.viper.repo_details.list.DetailsAdapter
import com.braintri.github.viper.repo_details.list.aggregate.DescriptionListItem
import com.braintri.github.viper.repo_details.list.aggregate.OwnerListItem
import com.braintri.github.viper.repo_details.list.aggregate.ParameterListItem
import com.braintri.github.viper.repo_details.list.aggregate.UrlListItem
import com.jakewharton.rxbinding2.support.v7.widget.navigationClicks
import com.mateuszkoslacz.moviper.base.view.activity.autoinject.passive.ViperAiPassiveActivity
import kotlinx.android.synthetic.main.activity_repo_details.*

class RepoDetailsActivity : ViperAiPassiveActivity<RepoDetailsContract.View>(), RepoDetailsContract.View {

    private val adapter = DetailsAdapter()

    override val repoName
        get() = args?.getString(REPO_NAME_ARGS)!!
    override val ownerName
        get() = args?.getString(REPO_OWNER_ARGS)!!
    override val navigationBackButtonClicks
        get() = toolbar.navigationClicks()
    override val urlClicksEvents = adapter.urlClicksEvents

    override fun injectViews() {
        super.injectViews()
        setToolbarTitle()
        setReyclerView()
    }

    override fun setDetailsData(details: RepoDetails) {
        adapter.listingItems = mutableListOf<ListingItem>().apply {
            add(OwnerListItem(details.owner))
            add(DescriptionListItem(details.description))
            add(ParameterListItem(getString(R.string.language), details.language))
            add(ParameterListItem(getString(R.string.stars), details.stars.toString()))
            add(ParameterListItem(getString(R.string.watchers), details.watchersCount.toString()))
            add(ParameterListItem(getString(R.string.subscribers), details.subscribersCount.toString()))
            add(ParameterListItem(getString(R.string.forks), details.forksCount.toString()))
            add(ParameterListItem(getString(R.string.openIssues), details.openIssuesCount.toString()))
            add(ParameterListItem(getString(R.string.createdAt), details.createdAt.getFormattedString(DATE_WITH_TIME_FORMAT)))
            add(ParameterListItem(getString(R.string.updatedAt), details.updatedAt.getFormattedString(DATE_WITH_TIME_FORMAT)))
            add(UrlListItem(getString(R.string.repoUrl), details.url))
            add(UrlListItem(getString(R.string.ownerUrl), details.owner.url))
        }
    }

    private fun setToolbarTitle() {
        toolbarTitle.text = repoName
    }

    override fun showLoading() {
        loadingState.visible()
        emptyState.gone()
        details.gone()
    }

    override fun showContent() {
        loadingState.gone()
        emptyState.gone()
        details.visible()
    }

    override fun showEmptyState() {
        loadingState.gone()
        emptyState.visible()
        details.gone()
    }

    override fun showError(throwable: Throwable) {
        Toast.makeText(this, throwable.toString(), Toast.LENGTH_LONG).show()
    }

    private fun setReyclerView() {
        details.adapter = adapter
        details.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun createPresenter() = RepoDetailsPresenter()

    override fun getLayoutId() = R.layout.activity_repo_details
}
