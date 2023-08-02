package com.example.myapplication.main.screens.general_screen

import com.google.gson.annotations.SerializedName

data class Photos(
    val id: String,
    val user: User?,
    val urls: Urls?,
)

class User(
    val id: String,
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
)





