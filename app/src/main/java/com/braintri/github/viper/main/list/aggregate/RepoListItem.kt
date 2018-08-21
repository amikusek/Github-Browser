package com.braintri.github.viper.main.list.aggregate

import com.braintri.github.data.entity.Repo
import com.braintri.github.view._base.ListingItem

val REPO_ITEM_TYPE = RepoListItem::class.java.hashCode()

data class RepoListItem(val repo: Repo) : ListingItem {

    override val type = REPO_ITEM_TYPE
}