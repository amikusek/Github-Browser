package com.braintri.github.test.data.remote.mapper

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.remote.mapper.OwnerMapper
import com.braintri.github.data.remote.model.OwnerModel
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OwnerMapperTest {

    val mapper = OwnerMapper()
    val model = OwnerModel(1, "login", "www.google.com", "www.google.com")
    val entity = Owner(1, "login", "www.google.com", "www.google.com")

    @Test
    fun `mapOrSkip returns list which contains items with non-null id or login fields`() {
        Assert.assertEquals(
                listOf(entity),
                mapper.mapOrSkip(listOf(model.copy(id = 1), model.copy(id = null), model.copy(login = null))))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when id is null`() {
        mapper.mapOrThrow(model.copy(id = null))
    }

    @Test(expected = IllegalStateException::class)
    fun `mapOrThrow throws Exception when login is null`() {
        mapper.mapOrThrow(model.copy(login = null))
    }
}
