package com.unsplash.sulatskov.domain.model

import com.unsplash.sulatskov.domain.model.dto.Urls
import com.unsplash.sulatskov.domain.model.dto.UserDto
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



