package com.neda.ecommerce.data.remote

import com.neda.ecommerce.data.model.Product
import com.neda.ecommerce.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApiService {
    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") productId: Int): Product
}