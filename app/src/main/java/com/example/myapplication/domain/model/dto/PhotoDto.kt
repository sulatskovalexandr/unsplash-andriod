package com.example.myapplication.domain.model.dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val user: UserDto?,
    val urls: Urls?,
    @SerializedName("created_at") val createdAt: String,
)

class UserDto(
    val id: String,
    @SerializedName("username")
    val userName: String,
//    val name: String,
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