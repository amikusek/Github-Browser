package com.braintri.github.test.data.remote.mapper

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.RepoDetails
import com.braintri.github.data.remote.mapper.RepoDetailsMapper
import com.braintri.github.data.remote.model.OwnerModel
import com.braintri.github.data.remote.model.RepoDetailsModel
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RepoDetailsMapperTest {

    val mapper = RepoDetailsMapper()
    val ownerModel = OwnerModel(1, "login", "www.google.com", "www.google.com")
    val repoDetailsModel = RepoDetailsModel(1, "Test", "Description", ownerModel, "Kotlin", "Repo Test", "www.google.com", 0, 0, 0, 0, 0, "1970-01-01T01:00:00Z", "1970-01-01T01:00:00Z")
    val ownerEntity = Owner(1, "login", "www.google.com", "www.google.com")
    val repoDetailsEntity = RepoDetails(1, "Test", "Repo Test", ownerEntity, "www.google.com", "Description", "Kotlin", 0, 0, 0, 0, 0, Date(0), Date(0))

    @Test
    fun `mapOrSkip returns list which contains items with non-null id or login fields`() {
        Assert.assertEquals(
                listOf(repoDetailsEntity),
                mapper.mapOrSkip(listOf(repoDetailsModel.copy(id = 1), repoDetailsModel.copy(id = null), repoDetailsModel.copy(owner = null))))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when id is null`() {
        mapper.mapOrThrow(repoDetailsModel.copy(id = null))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when login is null`() {
        mapper.mapOrThrow(repoDetailsModel.copy(owner = null))
    }
}
