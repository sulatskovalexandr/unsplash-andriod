package com.unsplash.sulatskov.domain.model

import com.google.gson.annotations.SerializedName

class CollectionDetails(
    val id: String,
    val title: String? = "",
    val description: String? = "",
    @SerializedName("total_photos")
    val totalPhotos: Int? = 0,
    @SerializedName("cover_photo")
    val coverPhoto: CoverPhoto,
    val user: User
)