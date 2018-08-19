package com.braintri.github.data.remote.api

import com.braintri.github.data.remote.model.SearchResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val SEARCH_PAGE_SIZE = 10

interface SearchApi {

    @GET("/search/repositories")
    fun getReposByQuery(@Query("q") query: String,
                        @Query("page") page: Int,
                        @Query("per_page") pageSize: Int): Single<SearchResponse>
}
