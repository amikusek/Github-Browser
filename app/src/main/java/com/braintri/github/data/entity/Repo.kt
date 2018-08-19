package com.braintri.github.data.entity

import java.util.*

data class Repo(val id: Int,
                val name: String,
                val fullName: String,
                val description: String,
                val url: String,
                val language: String,
                val owner: Owner,
                val createdAt: Date,
                val updatedAt: Date,
                val stars: Int)
