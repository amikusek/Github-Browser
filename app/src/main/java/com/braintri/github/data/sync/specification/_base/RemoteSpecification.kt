package com.braintri.github.data.sync.specification._base

import io.reactivex.Single
import retrofit2.Retrofit

interface RemoteSpecification : Specification {

    fun getResults(retrofit: Retrofit): Single<*>
}