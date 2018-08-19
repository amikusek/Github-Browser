package com.braintri.github.viper.main

import com.braintri.github.data.sync.repository.remote.repo.RepoRemoteRepository
import com.braintri.github.data.sync.specification.remote.RepoRemoteSpecification
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor

class MainInteractor : BaseRxInteractor(), MainContract.Interactor {

    override fun getRepos(query: String, page: Int) =
            RepoRemoteRepository()
                    .queryList(RepoRemoteSpecification(query, page))
}
