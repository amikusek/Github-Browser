package com.braintri.github.data.sync.repository.remote.repo

import com.braintri.github.constants.BASE_GITHUB_API_URL
import com.braintri.github.data.entity.Repo
import com.braintri.github.data.remote.RetrofitFactory
import com.braintri.github.data.remote.mapper.RepoMapper
import com.braintri.github.data.remote.model.RepoModel
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.specification._base.Specification

class RepoRemoteRepository : Repository<Repo> {

    private val retrofitFactory = RetrofitFactory(BASE_GITHUB_API_URL)
    private val mapper = RepoMapper()

    override fun queryList(specification: Specification) =
            retrofitFactory.createDefaultQuery(specification)
                    .map { mapper.mapOrSkip((it as? List<RepoModel>)) }

    override fun query(specification: Specification) =
            queryList(specification)
                    .map { it.first() }
}
