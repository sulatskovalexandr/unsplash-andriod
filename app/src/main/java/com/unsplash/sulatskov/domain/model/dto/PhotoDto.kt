package com.unsplash.sulatskov.domain.model.dto


import com.google.gson.annotations.SerializedName
import com.unsplash.sulatskov.domain.model.User

data class PhotoDto(
    val id: String,
    val user: User,
    val urls: Urls?,
    @SerializedName("created_at") val createdAt: String,
)

data class UserDto(
    val id: String = "",
    @SerializedName("username")
    val userName: String = "",
    val name: String = "",
    val location: String? = "",
    @SerializedName("profile_image")
    val profileImage: ProfileImage?,
)

data class ProfileImage(
    val small: String? = "",
    val medium: String? = "",
    val large: String? = "",
)

data class Urls(
    val raw: String? = "",
    val full: String? = "",
    val regular: String? = "",
    val small: String? = "",
)