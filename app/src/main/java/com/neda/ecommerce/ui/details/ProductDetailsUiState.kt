package com.neda.ecommerce.ui.details

import com.neda.ecommerce.data.model.Product

data class ProductDetailsUiState(
    val product: Product? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)