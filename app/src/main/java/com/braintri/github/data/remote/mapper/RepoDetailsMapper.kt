package com.braintri.github.data.remote.mapper

import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.data.remote.mapper._base.BaseMapper
import com.braintri.github.data.remote.model.RepoDetailsModel
import com.braintri.github.util.extension.mapRemoteDateToLocal

class RepoDetailsMapper : BaseMapper<RepoDetails, RepoDetailsModel>() {

    override fun mapOrReturnNull(model: RepoDetailsModel): RepoDetails? {
        return if (model.id == null || model.owner == null)
            null
        else
            RepoDetails(model.id,
                    model.name ?: "",
                    model.fullName ?: "",
                    OwnerMapper().mapOrSkip(listOf(model.owner)).first(),
                    model.url ?: "",
                    model.description ?: "",
                    model.language ?: "none",
                    model.forksCount ?: 0,
                    model.starsCount ?: 0,
                    model.watchersCount ?: 0,
                    model.subscribersCount ?: 0,
                    model.openIssuesCount ?: 0,
                    mapRemoteDateToLocal(model.createdAt),
                    mapRemoteDateToLocal(model.updatedAt))
    }
}
