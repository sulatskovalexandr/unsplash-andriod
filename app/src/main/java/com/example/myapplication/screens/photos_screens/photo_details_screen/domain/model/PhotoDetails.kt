package com.example.myapplication.screens.photos_screens.photo_details_screen.domain.model

import com.example.myapplication.screens.photos_screens.photo_screen.domain.dto.Urls
import com.example.myapplication.screens.photos_screens.photo_screen.domain.dto.UserDto
import com.google.gson.annotations.SerializedName

data class PhotoDetails(
    val id: String? = "",
    val width: Int? = 0,
    val height: Int? = 0,
    val likes: Int? = 0,
    val exif: Exif?,
    val location: Location?,
    val tags: List<Tag>?,
    val urls: Urls?,
    val user: UserDto?
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

class DownloadPhotoUrl(
    val url: String? = ""
)



