package com.unsplash.sulatskov.data_base

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unsplash.sulatskov.domain.model.Collection

/**
 * БД для хранения списка фото
 */
@Database(entities = [Collection::class], version = 1)

abstract class CollectionDataBase : RoomDatabase() {
    abstract fun collectionDao(): CollectionDao
}