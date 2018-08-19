package com.braintri.github.viper.repo_details

import com.braintri.github.data.sync.repository.remote.repo.RepoDetailsRemoteRepository
import com.braintri.github.data.sync.specification.remote.RepoDetailsRemoteSpecification
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor

class RepoDetailsInteractor : BaseRxInteractor(), RepoDetailsContract.Interactor {

    override fun getRepoDetails(owner: String, repo: String) =
            RepoDetailsRemoteRepository()
                    .queryList(RepoDetailsRemoteSpecification(owner, repo))
                    .map { it.first() }
}
