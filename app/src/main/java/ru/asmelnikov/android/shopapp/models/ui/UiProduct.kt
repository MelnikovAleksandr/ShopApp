package ru.asmelnikov.android.shopapp.models.ui

import ru.asmelnikov.android.shopapp.models.domain.Product

data class UiProduct(
    val isFavorite: Boolean = false,
    val product: Product,
    val isExpanded: Boolean = false,
    val isInCart: Boolean = false
)