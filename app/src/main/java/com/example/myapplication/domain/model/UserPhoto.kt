package com.example.myapplication.domain.model

import com.example.myapplication.domain.model.dto.Urls
import com.google.gson.annotations.SerializedName

class UserPhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("user")
    val user: User,
    @SerializedName("urls")
    val url: Urls
)


