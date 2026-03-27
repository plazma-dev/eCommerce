package com.neda.ecommerce.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_products")
    fun getAllFavorites(): Flow<List<FavoriteProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun insert(product: FavoriteProduct)

    @Delete
    fun delete(product: FavoriteProduct)

    @Query("SELECT EXISTS(SELECT * FROM favorite_products WHERE id = :id)")
    fun isFavorite(id: Int): Boolean
    //suspend fun isFavorite(id: Int): Boolean

    @Query("SELECT id FROM favorite_products")
    fun favoritesIds(): Flow<List<Int>>
}
