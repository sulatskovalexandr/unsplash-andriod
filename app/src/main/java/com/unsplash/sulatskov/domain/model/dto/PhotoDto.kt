package com.unsplash.sulatskov.domain.model.dto


import com.unsplash.sulatskov.domain.model.User
import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val user: User,
    val urls: Urls?,
    @SerializedName("created_at") val createdAt: String,
)

class UserDto(
    val id: String = "",
    @SerializedName("username")
    val userName: String = "",
    val location: String? = "",
    @SerializedName("profile_image")
    val profileImage: ProfileImage?,

    )

class
ProfileImage(
    val small: String? = "",
    val medium: String? = "",
    val large: String? = "",

    )

class Urls(
    val raw: String? = "",
    val full: String? = "",
    val regular: String? = "",
    val small: String? = "",
)