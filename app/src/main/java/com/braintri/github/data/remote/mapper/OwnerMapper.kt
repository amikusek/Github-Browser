package com.braintri.github.data.remote.mapper

import com.braintri.github.data.entity.Owner
import com.braintri.github.data.remote.mapper._base.BaseMapper
import com.braintri.github.data.remote.model.OwnerModel

class OwnerMapper : BaseMapper<Owner, OwnerModel>() {

    override fun mapOrReturnNull(model: OwnerModel): Owner? {
        return if (model.id == null || model.login == null)
            null
        else
            Owner(model.id, model.login, model.avatarUrl ?: "", model.url ?: "")
    }
}
