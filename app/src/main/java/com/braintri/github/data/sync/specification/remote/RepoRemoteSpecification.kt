package com.braintri.github.data.sync.specification.remote

import com.braintri.github.data.remote.api.SEARCH_PAGE_SIZE
import com.braintri.github.data.remote.api.SearchApi
import com.braintri.github.data.sync.specification._base.RemoteSpecification
import retrofit2.Retrofit

class RepoRemoteSpecification(private val query: String,
                              private val page: Int)
    : RemoteSpecification {

    override fun getResults(retrofit: Retrofit) =
            retrofit.create(SearchApi::class.java)
                    .getReposByQuery(query, page, SEARCH_PAGE_SIZE)
                    .map { it.items ?: listOf() }
}
