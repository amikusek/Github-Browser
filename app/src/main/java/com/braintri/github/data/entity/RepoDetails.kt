package com.braintri.github.data.entity

import java.util.*

data class RepoDetails(val id: Int,
                       val name: String,
                       val fullName: String,
                       val owner: Owner,
                       val url: String,
                       val description: String,
                       val language: String,
                       val forksCount: Int,
                       val stars: Int,
                       val watchersCount: Int,
                       val subscribersCount: Int,
                       val openIssuesCount: Int,
                       val createdAt: Date,
                       val updatedAt: Date)
