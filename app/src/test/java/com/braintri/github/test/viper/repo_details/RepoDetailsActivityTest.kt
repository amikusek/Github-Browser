package com.braintri.github.test.viper.repo_details

import android.content.Intent
import android.view.View
import com.braintri.github.BuildConfig
import com.braintri.github.constants.REPO_NAME_ARGS
import com.braintri.github.constants.REPO_OWNER_ARGS
import com.braintri.github.util.extension.gone
import com.braintri.github.util.extension.visible
import com.braintri.github.viper.repo_details.RepoDetailsActivity
import com.braintri.github.viper.repo_details.RepoDetailsPresenter
import com.braintri.github.viper.repo_details.list.DetailsAdapter
import com.braintri.github.viper.repo_details.list.aggregate.UrlListItem
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import kotlinx.android.synthetic.main.activity_repo_details.*
import kotlinx.android.synthetic.main.viewholder_url.view.*
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

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
class RepoDetailsActivityTest {

    @Mock
    lateinit var presenter: RepoDetailsPresenter
    val controller = Robolectric.buildActivity(RepoDetailsActivity::class.java, Intent().apply {
        putExtra(REPO_OWNER_ARGS, "user")
        putExtra(REPO_NAME_ARGS, "Android Test")
    })
    @InjectMocks
    val activity: RepoDetailsActivity = controller.get()
    val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        overrideSchedulers()
        MockitoAnnotations.initMocks(this)
        controller.setup()
    }

    @Test
    fun `loading view should be set to visible on start`() {
        Assert.assertEquals(View.VISIBLE, activity.loadingState.visibility)
    }

    @Test
    fun `repoName equals to value provided in args`() {
        Assert.assertEquals("Android Test", activity.args.getString(REPO_NAME_ARGS))
    }

    @Test
    fun `ownerName equals to value provided in args`() {
        Assert.assertEquals("user", activity.args.getString(REPO_OWNER_ARGS))
    }

    @Test
    fun `navigationBackButtonClicks emits event on toolbar on navigation icon clicks`() {
        val testObserver = activity.navigationBackButtonClicks.test()
        testObserver.assertNoValues().assertNoErrors().assertNotComplete()
        testObserver.onNext(Unit)
        testObserver.assertValue(Unit).assertNoErrors().assertNotComplete()
    }

    @Test
    fun `urlClicksEvents emits event with expected String on view holder clicks`() {
        val list = (1..5).map { "www.github.com/$it" }
        val adapter = activity.details.adapter as DetailsAdapter
        val testObserver = activity.urlClicksEvents.test()
        adapter.listingItems = list.map { UrlListItem("test", it) }
        activity.details.visible()
        activity.details.findViewHolderForAdapterPosition(0).itemView.url.performClick()
        testObserver.assertValue(list[0]).assertNoErrors().assertNotComplete()
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
    fun `showLoading hides content view`() {
        activity.details.visible()
        activity.showLoading()
        Assert.assertEquals(View.GONE, activity.details.visibility)
    }

    @Test
    fun `showContent hides loading view`() {
        activity.loadingState.visible()
        activity.showContent()
        Assert.assertEquals(View.GONE, activity.loadingState.visibility)
    }

    @Test
    fun `showContent hides empty view`() {
        activity.emptyState.visible()
        activity.showContent()
        Assert.assertEquals(View.GONE, activity.emptyState.visibility)
    }

    @Test
    fun `showContent shows content view`() {
        activity.details.gone()
        activity.showContent()
        Assert.assertEquals(View.VISIBLE, activity.details.visibility)
    }

    @Test
    fun `showEmptyState hides loading view`() {
        activity.loadingState.visible()
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
    fun `showEmptyState hides content view`() {
        activity.details.visible()
        activity.showEmptyState()
        Assert.assertEquals(View.GONE, activity.details.visibility)
    }

    @Test
    fun `showError shows Toast`() {
        activity.showError(Throwable("Error message"))
        Assert.assertEquals(Throwable("Error message").toString(), ShadowToast.getTextOfLatestToast())
    }

    fun overrideSchedulers() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        RxAndroidPlugins.setMainThreadSchedulerHandler { testScheduler }
    }
}