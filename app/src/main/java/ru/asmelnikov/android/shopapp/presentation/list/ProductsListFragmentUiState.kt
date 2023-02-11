package ru.asmelnikov.android.shopapp.presentation.list

import ru.asmelnikov.android.shopapp.models.ui.UiFilter
import ru.asmelnikov.android.shopapp.models.ui.UiProduct

sealed interface ProductsListFragmentUiState {
    data class Success(
        val filter: Set<UiFilter>,
        val products: List<UiProduct>
    ) : ProductsListFragmentUiState

    object Loading : ProductsListFragmentUiState
}
