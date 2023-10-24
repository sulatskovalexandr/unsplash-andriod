package com.example.myapplication.domain.model

import com.example.myapplication.domain.model.dto.ProfileImage
import com.google.gson.annotations.SerializedName

class User(
    val id: String,
    @SerializedName("username")
    val userName: String,
    val name: String,
    val bio: String,
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

class AccessToken(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String?,
    val scope: String?,
    @SerializedName("create_at")
    val createAt: Int?
)

class Me(
    val id: String,
    @SerializedName("username")
    val userName: String,
    val name: String,
    val email: String,
    val bio: String,
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