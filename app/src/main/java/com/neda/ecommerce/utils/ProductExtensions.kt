package com.neda.ecommerce.utils

import com.neda.ecommerce.data.local.FavoriteProduct
import com.neda.ecommerce.data.model.Product

fun FavoriteProduct.toProduct() = Product(
    id = this.id,
    title = this.name,
    price = this.price,
    thumbnail = this.thumbnail,
    description = this.description,
    images = listOf(this.image)
)

fun Product.toFavorite(): FavoriteProduct {
    return FavoriteProduct(
        id = this.id,
        name = this.title,
        price = this.price,
        thumbnail = this.thumbnail,
        description = this.description,
        image = this.images.first()
    )
}