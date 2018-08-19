package com.braintri.github.viper.repo_details.list.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.braintri.github.util.extension.loadUrl
import com.braintri.github.viper.repo_details.list.aggregate.OwnerListItem
import kotlinx.android.synthetic.main.viewholder_owner.view.*

class OwnerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: OwnerListItem) {
        itemView.run {
            avatar.loadUrl(item.owner.avatarUrl)
            name.text = item.owner.login
        }
    }
}
