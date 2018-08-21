package com.braintri.github.test.viper.main

import com.braintri.github.constants.REPO_NAME_ARGS
import com.braintri.github.constants.REPO_OWNER_ARGS
import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.Repo
import com.braintri.github.viper.main.MainActivity
import com.braintri.github.viper.main.MainRouting
import com.braintri.github.viper.repo_details.RepoDetailsActivity
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.robolectric.RobolectricTestRunner
import org.junit.Assert
import org.junit.Before
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import java.util.*

@RunWith(RobolectricTestRunner::class)
class MainRoutingTest {

    @InjectMocks
    val routing = MainRouting()

    val owner = Owner(1, "JohnDoe", "www.google.com", "www.google.com")
    val repo = Repo(1, "Example Repo", "Test", "Description", "www.google.com", "Kotlin", owner, Date(0), Date(0), 5)

    val activity = Robolectric.setupActivity(MainActivity::class.java)

    @Before
    fun setUp() {
        routing.attach(activity)
    }

    @Test
    fun `startRepoDetailsScreen starts PostDetailsActivity with expected args`() {
        routing.startRepoDetailsScreen(repo)
        val startedIntent = shadowOf(routing.relatedContext).nextStartedActivity
        Assert.assertEquals(repo.owner.login, startedIntent.extras.getString(REPO_OWNER_ARGS))
        Assert.assertEquals(repo.name, startedIntent.extras.getString(REPO_NAME_ARGS))
    }
}
