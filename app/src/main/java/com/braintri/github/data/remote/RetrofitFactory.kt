package com.braintri.github.data.remote

import com.braintri.github.data.sync.specification._base.RemoteSpecification
import com.braintri.github.data.sync.specification._base.Specification
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory(private val baseUrl: String) {

    val retrofit = createRetrofitInstance()

    fun createRetrofitInstance() =
            Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

    fun createDefaultQuery(specification: Specification) =
            (specification as RemoteSpecification)
                    .getResults(retrofit)
}
