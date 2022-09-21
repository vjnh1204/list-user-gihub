package com.example.testandroid.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id") val id: Long? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("bio") val bio: String? = null,
    @SerializedName("login") val login: String? = null,
    @SerializedName("site_admin") val siteAdmin: Boolean? = null,
    @SerializedName("location") val location: String? = null,
    @SerializedName("blog") val blog: String? = null,
)
