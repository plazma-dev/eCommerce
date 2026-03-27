package com.neda.ecommerce.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neda.ecommerce.data.local.FavoriteProduct
import com.neda.ecommerce.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    val favorites = repository.allFavorites.asLiveData()

    fun removeFavorite(product: FavoriteProduct) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeFromFavorites(product)
        }
    }
}