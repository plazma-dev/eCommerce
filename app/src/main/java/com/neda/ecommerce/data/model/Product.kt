package com.neda.ecommerce.data.model;

data class Product(
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Double,
    val thumbnail: String,
    val title: String
)
