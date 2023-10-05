package com.example.myapplication.domain.model

import com.example.myapplication.domain.model.dto.ProfileImage
import com.google.gson.annotations.SerializedName

class User(
    val id: String,
    @SerializedName("username")
    val userName: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("bio")
    val bio: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("total_likes")
    val totalLikes: Int,
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("total_collections")
    val totalCollections: Int,
    @SerializedName("profile_image")
    val profileImage: ProfileImage
)