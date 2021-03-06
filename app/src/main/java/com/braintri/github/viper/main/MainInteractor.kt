package com.braintri.github.viper.main

import com.braintri.github.data.entity.Repo
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.repository.remote.repo.RepoRemoteRepository
import com.braintri.github.data.sync.specification.remote.RepoRemoteSpecification
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor

class MainInteractor : BaseRxInteractor(), MainContract.Interactor {

    private var repoRepository: Repository<Repo> = RepoRemoteRepository()

    override fun getRepos(query: String, page: Int) =
            repoRepository
                    .queryList(RepoRemoteSpecification(query, page))
}
