package com.neda.ecommerce.ui.main

import com.neda.ecommerce.data.model.Product

data class ProductsUiState(
    val items: List<Product> = emptyList(),
    val isInitialLoading: Boolean = false,
    val isLoadingMore: Boolean = false,
    val error: String? = null,
    val endReached: Boolean = false
)