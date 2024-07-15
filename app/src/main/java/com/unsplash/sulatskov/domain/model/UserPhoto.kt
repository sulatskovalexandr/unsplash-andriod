package com.unsplash.sulatskov.domain.model

import com.google.gson.annotations.SerializedName
import com.unsplash.sulatskov.domain.model.dto.Urls

data class UserPhoto(
    @SerializedName("id")
    val id: String? = "",
    @SerializedName("user")
    val user: User,
    @SerializedName("urls")
    val url: Urls
)


