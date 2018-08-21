package com.braintri.github.test.viper.main

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.Repo
import com.braintri.github.data.sync.repository.remote._base.Repository
import com.braintri.github.data.sync.specification.remote.RepoRemoteSpecification
import com.braintri.github.viper.main.MainInteractor
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainInteractorTest {

    @Mock
    lateinit var repository: Repository<Repo>
    @InjectMocks
    val interactor = MainInteractor()

    val owner = Owner(1, "test", "www.google.com", "www.google.com")
    val repo = Repo(1, "Test", "Test", "Description", "www.google.com", "Kotlin", owner, Date(0), Date(0), 5)

    @Test
    fun `getRepos calls getResults method on repository with expected RepoRemoteSpecification`() {
        interactor.getRepos("testQuery", 0)
        Mockito.verify(repository).queryList(RepoRemoteSpecification("testQuery", 0))
    }

    @Test
    fun `getRepos streams expected repo from repository`() {
        Mockito.`when`(repository.queryList(RepoRemoteSpecification("testQuery", 0))).thenReturn(Single.just(listOf(repo)))
        interactor.getRepos("testQuery", 0)
                .test()
                .assertValue(listOf(repo))
                .assertNoErrors()
    }
}
