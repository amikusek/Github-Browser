package com.braintri.github.data.sync.specification.remote

import com.braintri.github.data.remote.api.RepoDetailsApi
import com.braintri.github.data.sync.specification._base.RemoteSpecification
import retrofit2.Retrofit

data class RepoDetailsRemoteSpecification(private val owner: String,
                                          private val name: String)
    : RemoteSpecification {

    override fun getResults(retrofit: Retrofit) =
            retrofit.create(RepoDetailsApi::class.java)
                    .getRepoDetails(owner, name)
}
