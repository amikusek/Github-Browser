package com.braintri.github.data.remote.model

import com.google.gson.annotations.SerializedName

data class RepoModel(val id: Int?,
                     val name: String?,
                     val description: String?,
                     val language: String?,
                     val owner: OwnerModel?,
                     val stars: Int?,
                     @SerializedName("full_name") val fullName: String?,
                     @SerializedName("html_url") val url: String?,
                     @SerializedName("created_at") val createdAt: String?,
                     @SerializedName("updated_at") val updatedAt: String?)
