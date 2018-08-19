package com.braintri.github.data.remote.mapper

import com.braintri.github.data.entity.Repo
import com.braintri.github.data.remote.mapper._base.BaseMapper
import com.braintri.github.data.remote.model.RepoModel
import com.braintri.github.util.extension.mapRemoteDateToLocal

class RepoMapper : BaseMapper<Repo, RepoModel>() {

    override fun mapOrReturnNull(model: RepoModel): Repo? {
        return if (model.id == null || model.owner == null)
            null
        else Repo(model.id, model.name ?: "", model.fullName ?: "", model.description ?: "",
                model.url ?: "",
                model.language ?: "",
                OwnerMapper().mapOrSkip(listOf(model.owner)).first(),
                mapRemoteDateToLocal(model.createdAt),
                mapRemoteDateToLocal(model.updatedAt),
                model.stars ?: 0)
    }
}
