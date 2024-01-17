package com.unsplash.sulatskov.domain.model

import com.unsplash.sulatskov.domain.model.dto.ProfileImage
import com.google.gson.annotations.SerializedName

class User(
    val id: String? = "",
    @SerializedName("username")
    val userName: String? = "",
    val name: String? = "",
    val bio: String? = "",
    val location: String? = "",
    @SerializedName("total_likes")
    val totalLikes: Int? = 0,
    @SerializedName("total_photos")
    val totalPhotos: Int? = 0,
    @SerializedName("total_collections")
    val totalCollections: Int? = 0,
    @SerializedName("profile_image")
    val profileImage: ProfileImage
)

class AccessToken(
    @SerializedName("access_token")
    val accessToken: String = "",
    @SerializedName("token_type")
    val tokenType: String? = "",
    val scope: String? = "",
    @SerializedName("create_at")
    val createAt: Int? = 0
)

class Me(
    val id: String = "",
    @SerializedName("username")
    val userName: String = "",
    val name: String = "",
    val email: String? = "",
    val bio: String? = "",
    val location: String? = "",
    @SerializedName("total_likes")
    val totalLikes: Int? = 0,
    @SerializedName("total_photos")
    val totalPhotos: Int? = 0,
    @SerializedName("total_collections")
    val totalCollections: Int = 0,
    @SerializedName("profile_image")
    val profileImage: ProfileImage
)