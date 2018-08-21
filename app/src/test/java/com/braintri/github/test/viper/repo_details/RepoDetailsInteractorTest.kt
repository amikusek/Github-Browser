package com.braintri.github.test.viper.repo_details

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.specification.remote.RepoDetailsRemoteSpecification
import com.braintri.github.viper.repo_details.RepoDetailsInteractor
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsInteractorTest {

    @Mock
    lateinit var repository: Repository<RepoDetails>
    @InjectMocks
    val interactor = RepoDetailsInteractor()

    val owner = Owner(1, "user", "www.google.com", "www.google.com")
    val repoDetails = RepoDetails(1, "test", "Repo Test", owner, "www.google.com", "Description", "Kotlin", 0, 0, 0, 0, 0, Date(0), Date(0))
    
    @Test
    fun `getRepoDetails calls getResults method on repository with expected RepoDetailsRemoteSpecification`() {
        interactor.getRepoDetails("user", "test")
        Mockito.verify(repository).query(RepoDetailsRemoteSpecification("user", "test"))
    }

    @Test
    fun `getRepoDetails streams expected repoDetails from repository`() {
        Mockito.`when`(repository.query(RepoDetailsRemoteSpecification("user", "test"))).thenReturn(Single.just(repoDetails))
        interactor.getRepoDetails("user", "test")
                .test()
                .assertValue(repoDetails)
                .assertNoErrors()
    }
}
