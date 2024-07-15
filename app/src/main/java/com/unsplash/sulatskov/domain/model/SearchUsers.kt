package com.unsplash.sulatskov.domain.model

import com.google.gson.annotations.SerializedName
import com.unsplash.sulatskov.domain.model.dto.PhotoDto

data class SearchUsers(
    val total: Int,
    @SerializedName("total_pages")
    val totalPages:Int,
    val results: List<User>
)
