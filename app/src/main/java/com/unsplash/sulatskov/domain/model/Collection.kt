package com.unsplash.sulatskov.domain.model

import com.unsplash.sulatskov.domain.model.dto.Urls
import com.google.gson.annotations.SerializedName

class Collection(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    @SerializedName("total_photos")
    val totalPhoto: Int = 0,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    val user: User,
    val private: Boolean

)

class CoverPhoto(
    val id: String = "",
    val likes: Int? = 0,
    val description: String? = "",
    val user: User,
    @SerializedName("urls")
    val url: Urls?,

    )
