package com.braintri.github.data.sync.repository.remote.repo

import com.braintri.github.constants.BASE_GITHUB_API_URL
import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.data.remote.RetrofitFactory
import com.braintri.github.data.remote.mapper.RepoDetailsMapper
import com.braintri.github.data.remote.model.RepoDetailsModel
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.specification._base.Specification

class RepoDetailsRemoteRepository : Repository<RepoDetails> {

    private val retrofitFactory = RetrofitFactory(BASE_GITHUB_API_URL)
    private val mapper = RepoDetailsMapper()

    override fun queryList(specification: Specification) =
            retrofitFactory.createDefaultQuery(specification)
                    .map { mapper.mapOrSkip(listOf((it as RepoDetailsModel))) }

    override fun query(specification: Specification) =
            retrofitFactory.createDefaultQuery(specification)
                    .map { mapper.mapOrThrow(it as RepoDetailsModel) }
}
