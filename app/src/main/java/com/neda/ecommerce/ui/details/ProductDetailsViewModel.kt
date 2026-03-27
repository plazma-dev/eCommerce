package com.neda.ecommerce.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class ProductDetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val productId: Int = savedStateHandle.get<Int>("productId") ?: -1

    private val _uiState = MutableStateFlow(ProductDetailsUiState(isLoading = true))
    val uiState: StateFlow<ProductDetailsUiState> = _uiState.asStateFlow()

    init {
        fetchProduct()
        isFavorite()
    }

    fun fetchProduct() {
        viewModelScope.launch {
            try {
                val response = repository.getProductDetails(productId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "",
                        product = response.getOrThrow()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = e.localizedMessage ?: "Error loading data",
                        product = null
                    )
                }
            }
        }
    }

    fun isFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = repository.isFavorite(productId)
            _uiState.update {
                it.copy(
                    isFavorite = isFavorite
                )
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch(Dispatchers.IO) {
            if(uiState.value.isFavorite) {
                uiState.value.product?.let { repository.removeFromFavorites(it.toFavorite()) }
                _uiState.update {
                    it.copy(
                        isFavorite = false
                    )
                }
            }
            else {
                uiState.value.product?.let { repository.addToFavorites(it.toFavorite()) }
                _uiState.update {
                    it.copy(
                        isFavorite = true
                    )
                }
            }

        }
    }
}
