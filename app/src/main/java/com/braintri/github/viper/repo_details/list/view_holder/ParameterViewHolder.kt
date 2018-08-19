package com.braintri.github.viper.repo_details.list.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.braintri.github.viper.repo_details.list.aggregate.ParameterListItem
import kotlinx.android.synthetic.main.viewholder_parameter.view.*

class ParameterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: ParameterListItem) {
        itemView.run {
            header.text = item.title
            value.text = item.value
        }
    }
}
