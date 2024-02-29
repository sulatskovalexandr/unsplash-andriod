package com.unsplash.sulatskov.domain.model

data class PhotoStatistics(
    val downloads: Downloads,
    val views: Views,
    val likes: Likes
)

data class Downloads(
    val total: Int? = 0
)

data class Views(
    val total: Int? = 0
)

data class Likes(
    val total: Int? = 0
)



