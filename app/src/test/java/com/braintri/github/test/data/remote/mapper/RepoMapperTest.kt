package com.braintri.github.test.data.remote.mapper

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.entity.Repo
import com.braintri.github.data.remote.mapper.RepoMapper
import com.braintri.github.data.remote.model.OwnerModel
import com.braintri.github.data.remote.model.RepoModel
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class RepoMapperTest {

    val mapper = RepoMapper()
    val ownerModel = OwnerModel(1, "login", "www.google.com", "www.google.com")
    val repoModel = RepoModel(1, "Test", "Description", "Kotlin", ownerModel, 5, "Test", "www.google.com", "1970-01-01T01:00:00Z", "1970-01-01T01:00:00Z")
    val ownerEntity = Owner(1, "login", "www.google.com", "www.google.com")
    val repoEntity = Repo(1, "Test", "Test", "Description", "www.google.com", "Kotlin", ownerEntity, Date(0), Date(0), 5)

    @Test
    fun `mapOrSkip returns list which contains items with non-null id or owner fields`() {
        Assert.assertEquals(
                listOf(repoEntity),
                mapper.mapOrSkip(listOf(repoModel.copy(id = 1), repoModel.copy(id = null), repoModel.copy(owner = null))))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when id is null`() {
        mapper.mapOrThrow(repoModel.copy(id = null))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when owner is null`() {
        mapper.mapOrThrow(repoModel.copy(owner = null))
    }
}
