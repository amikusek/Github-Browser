package com.braintri.github.data.remote.mapper._base

abstract class BaseMapper<Entity, Model> {

    protected abstract fun mapOrReturnNull(model: Model): Entity?

    fun mapOrSkip(models: List<Model>?): List<Entity> =
            mutableListOf<Entity>()
                    .apply {
                        models?.forEach {
                            mapOrReturnNull(it)?.let { add(it) }
                        }
                    }
}
