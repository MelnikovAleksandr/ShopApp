package ru.asmelnikov.android.shopapp.presentation.list

import ru.asmelnikov.android.shopapp.models.ui.UiFilter
import ru.asmelnikov.android.shopapp.models.ui.UiProduct
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import javax.inject.Inject

class ProductsListFragmentUiStateGenerator @Inject constructor() {

    fun generate(
        uiProducts: List<UiProduct>,
        productFilter: ApplicationState.ProductFilterInfo
    ): ProductsListFragmentUiState {
        if (uiProducts.isEmpty()) {
            return ProductsListFragmentUiState.Loading
        }

        val uiFilters = productFilter.filters.map { filter ->
            UiFilter(
                filter = filter,
                isSelected = productFilter.selectedFilter?.equals(filter) == true
            )

        }.toSet()

        val filterProducts = if (productFilter.selectedFilter == null) {
            uiProducts
        } else {
            uiProducts.filter {
                it.product.category == productFilter.selectedFilter.value
            }
        }
        return ProductsListFragmentUiState.Success(uiFilters, filterProducts)
    }
}