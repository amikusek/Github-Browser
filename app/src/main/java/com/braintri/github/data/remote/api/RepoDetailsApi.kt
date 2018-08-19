package com.braintri.github.data.remote.api

import com.braintri.github.data.remote.model.RepoDetailsModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface RepoDetailsApi {

    @GET("/repos/{owner}/{repo}")
    fun getRepoDetails(@Path("owner") owner: String,
                       @Path("repo") repo: String): Single<RepoDetailsModel>
}
