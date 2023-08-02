package com.example.myapplication.main.model

import com.example.myapplication.main.screens.general_screen.Urls
import com.example.myapplication.main.screens.general_screen.User
import com.google.gson.annotations.SerializedName

data class PhotoDetails(
    val id: String? = "",
    val width: Int? = 0,
    val height: Int? = 0,
    val downloads: Int? = 0,
    val likes: Int? = 0,
    val exif: Exif?,
    val location: Location?,
    val tags: List<Tag>?,
    val urls: Urls?,
    val user: User?
)

class Exif(
    val model: String? = "",
    val aperture: String = "",
    @SerializedName("exposure_time")
    val exposition: String = "",
    @SerializedName("focal_length")
    val focalLength: String = "",
    val iso: Int? = 0
)


class Location(
    val city: String = "",
    val country: String = ""
)

class Tag(
    val title: String? = ""
)




