package ru.asmelnikov.android.shopapp.presentation.list

import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.domain.Product
import javax.inject.Inject

class FilterGenerator @Inject constructor() {

    fun generateFrom(productsList: List<Product>): Set<Filter> {
        return productsList.groupBy {
            it.category
        }.map { mapEntry ->
            Filter(value = mapEntry.key, displayText = "${mapEntry.key} (${mapEntry.value.size})")
        }.toSet()
    }
}