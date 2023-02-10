package ru.asmelnikov.android.shopapp.redux

import ru.asmelnikov.android.shopapp.models.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList()
)
