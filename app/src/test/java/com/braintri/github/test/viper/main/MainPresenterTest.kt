package com.braintri.github.test.viper.main

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.Repo
import com.braintri.github.viper.main.*
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var view: MainActivity
    @Mock
    lateinit var interactor: MainContract.Interactor
    @Mock
    lateinit var routing: MainContract.Routing
    @InjectMocks
    val presenter = MainPresenter()
    val onSearchQueryEvents = PublishSubject.create<String>()
    val onLoadMoreEvents = PublishSubject.create<String>()
    val onRepoListItemClicksEvents = PublishSubject.create<Repo>()

    val owner = Owner(1, "test", "www.google.com", "www.google.com")
    val repo = Repo(1, "Test", "Test", "Description", "www.google.com", "Kotlin", owner, Date(0), Date(0), 5)

    init {
        overrideSchedulers()
    }

    @Before
    fun setUp() {
        Mockito.`when`(view.onSearchQueryChanges).thenReturn(onSearchQueryEvents)
        Mockito.`when`(view.onLoadMoreEvents).thenReturn(onLoadMoreEvents)
        Mockito.`when`(view.onRepoListItemClicksEvents).thenReturn(onRepoListItemClicksEvents)
        presenter.attachView(view)
    }

    @Test
    fun `onSearchQueryChanges gets repos with expected query`() {
        onSearchQueryEvents.onNext("testQuery")
        Mockito.verify(interactor).getRepos("testQuery", 0)
    }

    @Test
    fun `onSearchQueryChanges shows error when getting repos results in error`() {
        Mockito.`when`(view.currentPage).thenReturn(0)
        Mockito.`when`(interactor.getRepos("testQuery", 0)).then { throw ComparableException(-1) }
        onSearchQueryEvents.onNext("testQuery")
        Mockito.verify(view).showError(ComparableException(-1))
    }

    @Test
    fun `onLoadMoreEvents gets repos with expected query`() {
        onLoadMoreEvents.onNext("testQuery")
        Mockito.verify(interactor).getRepos("testQuery", 0)
    }

    @Test
    fun `onLoadMoreEvents gets repos with expected page`() {
        Mockito.`when`(view.currentPage).thenReturn(5)
        onLoadMoreEvents.onNext("testQuery")
        Mockito.verify(interactor).getRepos("testQuery", 5)
    }

    @Test
    fun `onLoadMoreEvents shows error when getting repos results in error`() {
        Mockito.`when`(interactor.getRepos("testQuery", 0)).then { throw ComparableException(-1) }
        onLoadMoreEvents.onNext("testQuery")
        Mockito.verify(view).showError(ComparableException(-1))
    }

    @Test
    fun `onRepoListItemClicksEvents starts PostDetailsScreen`() {
        onRepoListItemClicksEvents.onNext(repo)
        Mockito.verify(routing).startRepoDetailsScreen(repo.copy())
    }

    private fun overrideSchedulers() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    data class ComparableException(val errorCode: Int): Exception()
}