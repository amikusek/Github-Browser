package com.braintri.github.viper.repo_details.list.aggregate

import com.braintri.github.view._base.ListingItem

val DESCRIPTION_ITEM_TYPE = DescriptionListItem::class.java.hashCode()

class DescriptionListItem(val description: String) : ListingItem {

    override val type = DESCRIPTION_ITEM_TYPE
}
