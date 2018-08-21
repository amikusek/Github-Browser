package com.paginate.recycler

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.braintri.github.BuildConfig
import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.Repo
import com.braintri.github.util.extension.gone
import com.braintri.github.util.extension.visible
import com.braintri.github.viper.main.MainActivity
import com.braintri.github.viper.main.MainPresenter
import com.braintri.github.viper.main.list.RepoListAdapter
import com.braintri.github.viper.main.list.aggregate.RepoListItem
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import java.util.*
import java.util.concurrent.TimeUnit

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class MainActivityTest {

    @Mock
    lateinit var presenter: MainPresenter
    val controller = Robolectric.buildActivity(MainActivity::class.java)
    @InjectMocks
    val activity: MainActivity = controller.get()
    val testScheduler = TestScheduler()

    val owner = Owner(1, "test", "www.google.com", "www.google.com")
    val repo = Repo(1, "Test", "Test", "Description", "www.google.com", "Kotlin", owner, Date(0), Date(0), 5)

    @Before
    fun setUp() {
        overrideSchedulers()
        MockitoAnnotations.initMocks(this)
        controller.setup()
    }

    @Test
    fun `loading view should be set to gone on start`() {
        Assert.assertEquals(View.GONE, activity.loadingState.visibility)
    }

    @Test
    fun `onSearchQueryChanges emits event on SearchView query changes after 500ms`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("test", false)
        testScheduler.advanceTimeTo(500, TimeUnit.MILLISECONDS)
        testObserver.assertValue("test").assertNoErrors().assertNotComplete()
    }

    @Test
    fun `onSearchQueryChanges does not emit event on SearchView query changes in time less than 500ms`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("test", false)
        testScheduler.advanceTimeTo(499, TimeUnit.MILLISECONDS)
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
    }

    @Test
    fun `onSearchQueryChanges drops first event on SearchView query changes when another event is emitted earlier than 500ms after first one`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("test1", false)
        testScheduler.advanceTimeTo(100, TimeUnit.MILLISECONDS)
        activity.searchView.setQuery("test2", false)
        testScheduler.advanceTimeTo(600, TimeUnit.MILLISECONDS)
        testObserver.assertNever("test1").assertNoErrors().assertNotComplete()
    }

    @Test
    fun `onSearchQueryChanges emits second event on SearchView query changes when second event is emitted earlier than 500ms after first one`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("test1", false)
        testScheduler.advanceTimeTo(100, TimeUnit.MILLISECONDS)
        activity.searchView.setQuery("test2", false)
        testScheduler.advanceTimeTo(600, TimeUnit.MILLISECONDS)
        testObserver.assertValue("test2").assertNoErrors().assertNotComplete()
    }

    @Test
    fun `onSearchQuery emits event when query has length greater than 3 chars`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("test", false)
        testScheduler.advanceTimeTo(500, TimeUnit.MILLISECONDS)
        testObserver.assertValue("test").assertNoErrors().assertNotComplete()
    }

    @Test
    fun `onSearchQuery does not emit event when query has length less than or equal 3 chars`() {
        val testObserver = activity.onSearchQueryChanges.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        activity.searchView.setQuery("tst", false)
        testScheduler.advanceTimeTo(500, TimeUnit.MILLISECONDS)
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
    }

    @Test
    fun `setItemsOnList set items on list adapter`() {
        activity.setItemsOnList(listOf(repo, repo.copy(id = 2), repo.copy(id = 3)))
        val adapter = (activity.results.adapter as WrapperAdapter).wrappedAdapter as RepoListAdapter
        Assert.assertEquals(listOf(RepoListItem(repo), RepoListItem(repo.copy(id = 2)), RepoListItem(repo.copy(id = 3))), adapter.listingItems)
    }

    @Test
    fun `setItemsOnList increments currentPage`() {
        val currentPage = activity.currentPage
        activity.setItemsOnList(listOf(repo, repo.copy(id = 2), repo.copy(id = 3)))
        Assert.assertEquals(currentPage + 1, activity.currentPage)
    }

    @Test
    fun `setItemsOnList set isLoading to false`() {
        activity.setItemsOnList((1..20).map { repo.copy(it) })
        Assert.assertFalse(activity.isPageLoading)
    }

    @Test
    fun `addToList appends items to adapter list`() {
        activity.setItemsOnList((1..10).map { repo.copy(it) })
        activity.addToList(((11..20).map { repo.copy(it) }))
        val adapter = (activity.results.adapter as WrapperAdapter).wrappedAdapter as RepoListAdapter
        Assert.assertEquals((1..20).map { RepoListItem(repo.copy(it)) }, adapter.listingItems)
    }

    @Test
    fun `addToList increments currentPage`() {
        val currentPage = activity.currentPage
        activity.addToList((1..10).map { repo.copy(it) })
        Assert.assertEquals(currentPage + 1, activity.currentPage)
    }

    @Test
    fun `addToList set isLoading to false`() {
        activity.addToList((1..10).map { repo.copy(it) })
        Assert.assertFalse(activity.isPageLoading)
    }

    @Test
    fun `addToList set hasLoadedAllItems when parameter is empty list`() {
        activity.addToList(listOf())
        Assert.assertTrue(activity.hasLoadedAllItems)
    }

    @Test
    fun `showLoading shows loading view`() {
        activity.loadingState.gone()
        activity.showLoading()
        Assert.assertEquals(View.VISIBLE, activity.loadingState.visibility)
    }

    @Test
    fun `showLoading hides empty view`() {
        activity.emptyState.visible()
        activity.showLoading()
        Assert.assertEquals(View.GONE, activity.emptyState.visibility)
    }

    @Test
    fun `showLoading hides initial view`() {
        activity.initialState.visible()
        activity.showLoading()
        Assert.assertEquals(View.GONE, activity.initialState.visibility)
    }

    @Test
    fun `showLoading hides content view`() {
        activity.results.visible()
        activity.showLoading()
        Assert.assertEquals(View.GONE, activity.results.visibility)
    }

    @Test
    fun `showInitialState hides loading view`() {
        activity.loadingState.gone()
        activity.showInitialState()
        Assert.assertEquals(View.GONE, activity.loadingState.visibility)
    }

    @Test
    fun `showInitialState hides empty view`() {
        activity.emptyState.visible()
        activity.showInitialState()
        Assert.assertEquals(View.GONE, activity.emptyState.visibility)
    }

    @Test
    fun `showInitialState shows initial view`() {
        activity.initialState.visible()
        activity.showInitialState()
        Assert.assertEquals(View.VISIBLE, activity.initialState.visibility)
    }

    @Test
    fun `showInitialState hides content view`() {
        activity.results.visible()
        activity.showInitialState()
        Assert.assertEquals(View.GONE, activity.results.visibility)
    }

    @Test
    fun `showEmptyState hides loading view`() {
        activity.loadingState.gone()
        activity.showEmptyState()
        Assert.assertEquals(View.GONE, activity.loadingState.visibility)
    }

    @Test
    fun `showEmptyState shows empty view`() {
        activity.emptyState.gone()
        activity.showEmptyState()
        Assert.assertEquals(View.VISIBLE, activity.emptyState.visibility)
    }

    @Test
    fun `showEmptyState hides initial view`() {
        activity.initialState.visible()
        activity.showEmptyState()
        Assert.assertEquals(View.GONE, activity.initialState.visibility)
    }

    @Test
    fun `showEmptyState hides content view`() {
        activity.results.visible()
        activity.showEmptyState()
        Assert.assertEquals(View.GONE, activity.results.visibility)
    }

    @Test
    fun `showList hides loading view`() {
        activity.loadingState.gone()
        activity.showList()
        Assert.assertEquals(View.GONE, activity.loadingState.visibility)
    }

    @Test
    fun `showList hides empty view`() {
        activity.emptyState.gone()
        activity.showList()
        Assert.assertEquals(View.GONE, activity.emptyState.visibility)
    }

    @Test
    fun `showList hides initial view`() {
        activity.initialState.visible()
        activity.showList()
        Assert.assertEquals(View.GONE, activity.initialState.visibility)
    }

    @Test
    fun `showList shows content view`() {
        activity.results.visible()
        activity.showList()
        Assert.assertEquals(View.VISIBLE, activity.results.visibility)
    }

    @Test
    fun `showList scrolls list to top`() {
        activity.setItemsOnList((1..20).map { repo.copy(it) })
        activity.results.scrollToPosition(15)
        activity.showList()
        Assert.assertEquals(0, (activity.results.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
    }

    @Test
    fun `showError shows Toast`() {
        activity.showError(Throwable("Error message"))
        Assert.assertEquals(Throwable("Error message").toString(), ShadowToast.getTextOfLatestToast())
    }

    @Test
    fun `onRepoListItemClicksEvents emits event with expected Repo on view holder clicks`() {
        val list = (1..10).map { repo.copy(it) }
        val adapter = (activity.results.adapter as WrapperAdapter).wrappedAdapter as RepoListAdapter
        val testObserver = activity.onRepoListItemClicksEvents.test()
        adapter.listingItems = list.map { RepoListItem(it) }
        activity.results.visible()
        activity.results.findViewHolderForAdapterPosition(0).itemView.performClick()
        testObserver.assertValue(list[0]).assertNoErrors().assertNotComplete()
    }

    fun overrideSchedulers() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
    }
}
