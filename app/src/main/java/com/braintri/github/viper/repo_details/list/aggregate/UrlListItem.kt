package com.braintri.github.viper.repo_details.list.aggregate

import com.braintri.github.view._base.ListingItem

val URL_ITEM_TYPE = UrlListItem::class.java.hashCode()

class UrlListItem(val title: String,
                  val url: String) : ListingItem {

    override val type = URL_ITEM_TYPE
}
