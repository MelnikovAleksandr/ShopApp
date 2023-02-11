package ru.asmelnikov.android.shopapp.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.asmelnikov.android.shopapp.databinding.FragmentProductListBinding
import ru.asmelnikov.android.shopapp.models.ui.UiFilter
import ru.asmelnikov.android.shopapp.models.ui.UiProduct


@AndroidEntryPoint
class ProductListFragment : Fragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProductListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = UiProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)

        combine(
            viewModel.store.stateFlow.map { it.products },
            viewModel.store.stateFlow.map { it.favoriteProductIds },
            viewModel.store.stateFlow.map { it.expandedProductIds },
            viewModel.store.stateFlow.map { it.productFilterInfo },
            viewModel.store.stateFlow.map { it.inCartProducts }

        ) { listOfProducts, setOfFavoriteIds, setExpandedIds, productFilter, inCartProductsIds ->

            if (listOfProducts.isEmpty()) {
                return@combine ProductsListFragmentUiState.Loading
            }

            val uiProducts = listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavorite = setOfFavoriteIds.contains(product.id),
                    isExpanded = setExpandedIds.contains(product.id),
                    isInCart = inCartProductsIds.contains(product.id)
                )
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

            return@combine ProductsListFragmentUiState.Success(uiFilters, filterProducts)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiProducts ->
            controller.setData(uiProducts)
        }
        viewModel.refreshProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}