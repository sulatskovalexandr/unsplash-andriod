package com.unsplash.sulatskov.data_base

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unsplash.sulatskov.domain.model.Collection

/**
 * Интерфейс с запросами с БД
 */
@Dao
interface CollectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCollections(collections: List<Collection>)

    @Query("SELECT * FROM ${Collection.TABLE_NAME} order by createdTime desc limit :count")
    suspend fun getCollections(count: Int): List<Collection>

    @Query("SELECT * FROM ${Collection.TABLE_NAME} WHERE id =:collectionId")
    suspend fun getCollection(collectionId: String): Collection
}
