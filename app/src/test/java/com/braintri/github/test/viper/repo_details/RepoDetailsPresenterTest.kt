package com.braintri.github.test.viper.repo_details

import com.braintri.github.viper.repo_details.RepoDetailsActivity
import com.braintri.github.viper.repo_details.RepoDetailsContract
import com.braintri.github.viper.repo_details.RepoDetailsPresenter
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

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsPresenterTest {

    @Mock
    lateinit var view: RepoDetailsActivity
    @Mock
    lateinit var interactor: RepoDetailsContract.Interactor
    @Mock
    lateinit var routing: RepoDetailsContract.Routing
    @InjectMocks
    val presenter = RepoDetailsPresenter()
    val navigationBackButtonClicks = PublishSubject.create<Unit>()
    val urlClicksEvents = PublishSubject.create<String>()

    @Before
    fun setUp() {
        Mockito.`when`(view.navigationBackButtonClicks).thenReturn(navigationBackButtonClicks)
        Mockito.`when`(view.urlClicksEvents).thenReturn(urlClicksEvents)
        presenter.attachView(view)
    }

    init {
        overrideSchedulers()
    }

    @Test
    fun `navigationBackButtonClicks finish RepoDetailsActivity`() {
        navigationBackButtonClicks.onNext(Unit)
        Mockito.verify(routing).closeScreen()
    }

    @Test
    fun `urlClicksEvents starts Browser App with provided url`() {
        urlClicksEvents.onNext("www.github.com")
        Mockito.verify(routing).openWebsiteInBrowser("www.github.com")
    }

    private fun overrideSchedulers() {
        RxAndroidPlugins.reset()
        RxJavaPlugins.reset()
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxAndroidPlugins.setMainThreadSchedulerHandler { Schedulers.trampoline() }
    }
}