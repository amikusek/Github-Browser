package com.braintri.github.viper.repo_details

import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.repository.remote.repo.RepoDetailsRemoteRepository
import com.braintri.github.data.sync.specification.remote.RepoDetailsRemoteSpecification
import com.mateuszkoslacz.moviper.base.interactor.BaseRxInteractor

class RepoDetailsInteractor : BaseRxInteractor(), RepoDetailsContract.Interactor {

    private var detailsRepository: Repository<RepoDetails> = RepoDetailsRemoteRepository()

    override fun getRepoDetails(owner: String, repo: String) =
            detailsRepository
                    .query(RepoDetailsRemoteSpecification(owner, repo))
}
