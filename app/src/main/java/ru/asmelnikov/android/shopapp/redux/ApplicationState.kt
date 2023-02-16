package ru.asmelnikov.android.shopapp.redux

import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.models.network.NetworkUser

data class ApplicationState(
    val user: NetworkUser? = null,
    val products: List<Product> = emptyList(),
    val favoriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val inCartProducts: Set<Int> = emptySet(),
    val cartQuantitiesMap: Map<Int, Int> = emptyMap()

) {
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
