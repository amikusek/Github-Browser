package com.braintri.github.viper.main.list.view_holder

import android.support.v7.widget.RecyclerView
import android.view.View
import com.braintri.github.data.entity.Repo
import com.braintri.github.util.extension.DATE_WITH_TIME_FORMAT
import com.braintri.github.util.extension.getFormattedString
import com.braintri.github.util.extension.retrySubscribe
import com.braintri.github.util.extension.throttleClicks
import com.braintri.github.viper.main.list.aggregate.RepoListItem
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.viewholder_repository.view.*

class RepoViewHolder(itemView: View, val itemClicks: PublishSubject<Repo>) : RecyclerView.ViewHolder(itemView) {

    private var clicksDisposable: Disposable? = null

    fun bind(item: RepoListItem) {
        itemView.run {
            name.text = item.repo.name
            owner.text = item.repo.owner.login
            language.text = item.repo.language
            description.text = item.repo.description
            stars.text = item.repo.stars.toString()
            lastUpdateDate.text = item.repo.updatedAt.getFormattedString(DATE_WITH_TIME_FORMAT)

            clicksDisposable =
                    itemView
                            .throttleClicks()
                            .retrySubscribe(onNext = { itemClicks.onNext(item.repo) })
        }
    }

    fun unbind() = clicksDisposable?.dispose()
}
