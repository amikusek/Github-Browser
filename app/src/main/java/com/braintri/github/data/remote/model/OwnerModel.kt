package com.braintri.github.data.remote.model

import com.google.gson.annotations.SerializedName

data class OwnerModel(val id: Int?,
                      val login: String?,
                      @SerializedName("html_url") val url: String?,
                      @SerializedName("avatar_url") val avatarUrl: String?)
