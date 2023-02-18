package ru.asmelnikov.android.shopapp.presentation.explore

import ru.asmelnikov.android.shopapp.models.ui.UiProduct

sealed class ExploreFragmentViewState {
    object Loading : ExploreFragmentViewState()
    data class Error(val errorMessage: String) : ExploreFragmentViewState()
    data class Success(
        val selectedUiProduct: UiProduct,
        val quantity: Int,
        val allUiProducts: List<UiProduct>
    ) : ExploreFragmentViewState()
}