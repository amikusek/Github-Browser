package com.braintri.github.viper.repo_details.list.aggregate

import com.braintri.github.data.entity.Owner
import com.braintri.github.view._base.ListingItem

val OWNER_ITEM_TYPE = OwnerListItem::class.java.hashCode()

class OwnerListItem(val owner: Owner) : ListingItem {

    override val type = OWNER_ITEM_TYPE
}