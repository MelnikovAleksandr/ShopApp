package ru.asmelnikov.android.shopapp.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.asmelnikov.android.shopapp.hilt.ProductRepository
import ru.asmelnikov.android.shopapp.models.domain.Filter
import ru.asmelnikov.android.shopapp.models.domain.Product
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import ru.asmelnikov.android.shopapp.redux.Store
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    val store: Store<ApplicationState>,
    private val filterGenerator: FilterGenerator
) : ViewModel() {

    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productRepository.fetchAllProducts()
        val filters: Set<Filter> = filterGenerator.generateFrom(products)
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filters,
                    selectedFilter = null
                )
            )
        }
    }
}