package com.braintri.github.test.viper.repo_details

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import com.braintri.github.R.id.url
import com.braintri.github.constants.REPO_NAME_ARGS
import com.braintri.github.constants.REPO_OWNER_ARGS
import com.braintri.github.viper.repo_details.RepoDetailsActivity
import com.braintri.github.viper.repo_details.RepoDetailsRouting
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class RepoDetailsRoutingTest {

    @InjectMocks
    val routing = RepoDetailsRouting()

    val activity = Robolectric.buildActivity(RepoDetailsActivity::class.java, Intent().apply {
        putExtra(REPO_OWNER_ARGS, "user")
        putExtra(REPO_NAME_ARGS, "Android Test")
    })

    @Before
    fun setUp() {
        routing.attach(activity.get())
    }

    @Test
    fun `closeScreen finish activity`() {
        routing.closeScreen()
        val shadowActivity = shadowOf(activity.get())
        Assert.assertTrue(shadowActivity.isFinishing)
    }

    @Test
    fun `openWebsiteInBrowser starts browser with url in args`() {
        routing.openWebsiteInBrowser("www.github.com")
        val startedIntent = shadowOf(routing.relatedContext).nextStartedActivity
        Assert.assertEquals(Uri.parse("www.github.com"), startedIntent.data)
        Assert.assertEquals(ACTION_VIEW, startedIntent.action)
    }
}
