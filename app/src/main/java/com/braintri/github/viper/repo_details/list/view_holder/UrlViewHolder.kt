package com.braintri.github.viper.repo_details.list.view_holder

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import com.braintri.github.util.extension.retrySubscribe
import com.braintri.github.util.extension.throttleClicks
import com.braintri.github.viper.repo_details.list.aggregate.UrlListItem
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.viewholder_url.view.*

class UrlViewHolder(itemView: View,
                    val urlClicksEvents: PublishSubject<String>) : RecyclerView.ViewHolder(itemView) {

    private var clicksDisposable: Disposable? = null

    fun bind(item: UrlListItem) {
        itemView.run {
            url.paintFlags = url.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            header.text = item.title
            url.text = item.url

            clicksDisposable =
                    url.throttleClicks()
                            .retrySubscribe(
                                    onNext = { urlClicksEvents.onNext(item.url) },
                                    onError = { it.printStackTrace() })
        }
    }

    fun unbind() = clicksDisposable?.dispose()
}
