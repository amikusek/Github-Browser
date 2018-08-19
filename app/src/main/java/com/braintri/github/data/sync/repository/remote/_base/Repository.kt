package com.braintri.github.data.sync.repository.remote._base

import com.braintri.github.data.sync.specification._base.Specification
import io.reactivex.Single

interface Repository<T> {

    fun query(specification: Specification): Single<T>
    fun queryList(specification: Specification): Single<List<T>>
}
