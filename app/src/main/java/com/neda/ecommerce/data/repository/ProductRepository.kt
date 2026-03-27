package com.neda.ecommerce.data.repository

import com.neda.ecommerce.data.remote.ProductApiService
import com.neda.ecommerce.data.local.FavoriteDao
import com.neda.ecommerce.data.local.FavoriteProduct
import com.neda.ecommerce.data.model.Product
import com.neda.ecommerce.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow

class ProductRepository (
    private val apiService: ProductApiService,
    private val favoriteDao: FavoriteDao
) {
    val allFavorites: Flow<List<FavoriteProduct>> = favoriteDao.getAllFavorites()

    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): Result<ProductResponse> {
        return try {
            val response = apiService.getProducts(limit, skip)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getProductDetails(id: Int): Result<Product> {
        return try {
            val product = apiService.getProductById(id)
            Result.success(product)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun addToFavorites(product: FavoriteProduct) {
        favoriteDao.insert(product)
    }

    fun removeFromFavorites(product: FavoriteProduct) {
        favoriteDao.delete(product)
    }

    suspend fun isFavorite(productId: Int): Boolean {
        return favoriteDao.isFavorite(productId)
    }

}
