package com.neda.ecommerce.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.neda.ecommerce.data.model.Product
import com.neda.ecommerce.data.repository.ProductRepository
import com.neda.ecommerce.utils.toFavorite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val limit = 10
    private var skip = 0

    private val _uiState = MutableStateFlow(ProductsUiState(isInitialLoading = true))
    val uiState: StateFlow<ProductsUiState> = _uiState.asStateFlow()

    val favorites = repository.allFavorites.asLiveData()

    init {
        loadNextPage()
    }

    fun loadNextPage() {
        val currentState = _uiState.value

        if (currentState.isLoadingMore || currentState.endReached) return

        viewModelScope.launch {

            _uiState.update {
                it.copy(
                    isInitialLoading = it.items.isEmpty(),
                    isLoadingMore = it.items.isNotEmpty(),
                    error = null
                )
            }

            try {
                val response = repository.getProducts(limit, skip).getOrThrow()
                skip += limit

                val newList = currentState.items + response.products
                val endReached = newList.size >= response.total

                _uiState.update {
                    it.copy(
                        items = newList,
                        isInitialLoading = false,
                        isLoadingMore = false,
                        endReached = endReached
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isInitialLoading = false,
                        isLoadingMore = false,
                        error = e.localizedMessage ?: "Error"
                    )
                }
            }
        }
    }

    fun refresh() {
        skip = 0
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    items = emptyList(),
                    isInitialLoading = true,
                    isLoadingMore = false,
                    error = null,
                    endReached = false
                )
            }
            loadNextPage()
        }
    }

    fun toggleFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            favorites.value?.let { favorites ->
                if(favorites.map { it.id }.contains(product.id)) {
                    repository.removeFromFavorites(product.toFavorite())
                } else {
                    repository.addToFavorites(product.toFavorite())
                }
            }
        }
    }
}