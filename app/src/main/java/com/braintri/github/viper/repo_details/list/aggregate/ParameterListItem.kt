package com.braintri.github.viper.repo_details.list.aggregate

import com.braintri.github.view._base.ListingItem

val PARAMETER_ITEM_TYPE = ParameterListItem::class.java.hashCode()

class ParameterListItem(val title: String,
                        val value: String) : ListingItem {

    override val type = PARAMETER_ITEM_TYPE
}