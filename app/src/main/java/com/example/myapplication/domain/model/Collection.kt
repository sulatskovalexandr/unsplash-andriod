package com.example.myapplication.domain.model

import com.example.myapplication.domain.model.dto.Urls
import com.google.gson.annotations.SerializedName

class Collection(
    val id: String,
    val title: String,
    val description: String,
    @SerializedName("total_photos")
    val totalPhoto: Int,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    val user: User,
    val private: Boolean = false

)

class CoverPhoto(
    val id: String,
    val likes: Int,
    val description: String,
    val user: User,
    @SerializedName("urls")
    val url: Urls,

    )
