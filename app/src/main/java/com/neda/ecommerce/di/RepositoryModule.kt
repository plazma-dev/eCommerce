package com.neda.ecommerce.di

import com.neda.ecommerce.data.remote.ProductApiService
import com.neda.ecommerce.data.local.FavoriteDao
import com.neda.ecommerce.data.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        apiService: ProductApiService,
        favoriteDao: FavoriteDao
    ): ProductRepository {
        return ProductRepository(apiService, favoriteDao)
    }
}