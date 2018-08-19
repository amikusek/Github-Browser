package com.braintri.github.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepoDetailsModel(val id: Int?,
                            val name: String?,
                            val description: String?,
                            val owner: OwnerModel?,
                            val language: String?,
                            @SerializedName("full_name") val fullName: String?,
                            @SerializedName("html_url") val url: String?,
                            @SerializedName("forks_count") val forksCount: Int?,
                            @SerializedName("stargazers_count") val starsCount: Int?,
                            @SerializedName("watchers_count") val watchersCount: Int?,
                            @SerializedName("subscribers_count") val subscribersCount: Int?,
                            @SerializedName("open_issues_count") val openIssuesCount: Int?,
                            @SerializedName("created_at") val createdAt: String?,
                            @SerializedName("updated_at") val updatedAt: String?)
