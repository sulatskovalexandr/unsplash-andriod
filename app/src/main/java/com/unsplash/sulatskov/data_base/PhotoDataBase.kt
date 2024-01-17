package com.unsplash.sulatskov.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unsplash.sulatskov.domain.model.Photo

/**
 * БД для хранения списка фото
 */
@Database(entities = [Photo::class], version = 1)

abstract class PhotoDataBase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}