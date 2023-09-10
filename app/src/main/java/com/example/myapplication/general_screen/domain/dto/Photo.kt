package com.example.myapplication.general_screen.domain.dto

import com.google.gson.annotations.SerializedName

data class PhotoDto(
    val id: String,
    val user: UserDto?,
    val urls: Urls?,
    @SerializedName("created_at") val createdAt: String,
)

class UserDto(
    val id: String,
    val name: String,
    @SerializedName("username")
    val userName: String,
    val location: String? = "",
    @SerializedName("profile_image")
    val profileImage: ProfileImage?,

    )

class ProfileImage(
    val medium: String? = "",
)

class Urls(
    val regular: String? = "",
    val small: String? = "",
)