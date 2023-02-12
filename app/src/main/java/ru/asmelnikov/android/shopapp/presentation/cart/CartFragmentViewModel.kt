package ru.asmelnikov.android.shopapp.presentation.cart

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import ru.asmelnikov.android.shopapp.redux.Store
import ru.asmelnikov.android.shopapp.redux.reducer.UiProductListReducer
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer
) : ViewModel() {

}