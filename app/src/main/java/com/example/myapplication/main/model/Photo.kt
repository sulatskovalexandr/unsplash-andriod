package com.example.myapplication.main.screens.general_screen

data class Photos(
    val id: String,
    val user: User,
)

class User(
    val id: String,
    val username: String,
    val location: String,
    val profile_image: ProfileImage,
    val urls: Urls,
)

class ProfileImage(
    val medium: String,
)

class Urls(
    val regular: String,
)





