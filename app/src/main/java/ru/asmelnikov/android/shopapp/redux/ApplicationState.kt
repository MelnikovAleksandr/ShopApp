package ru.asmelnikov.android.shopapp.redux

import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo()

) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
