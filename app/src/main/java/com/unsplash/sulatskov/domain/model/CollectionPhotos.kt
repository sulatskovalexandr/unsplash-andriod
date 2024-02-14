package com.unsplash.sulatskov.domain.model

import com.unsplash.sulatskov.domain.model.dto.Urls

class CollectionPhotos(
    val id: String,
    val user: User,
    val urls: Urls
    )