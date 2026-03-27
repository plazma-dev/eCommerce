package com.neda.ecommerce.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val thumbnail: String,
    val image: String,
    val description: String
)
