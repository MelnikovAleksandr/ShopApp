package ru.asmelnikov.android.shopapp.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import ru.asmelnikov.android.shopapp.databinding.FragmentCartBinding
import ru.asmelnikov.android.shopapp.models.ui.UiProduct

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<CartFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CartFragmentEpoxyController()
        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.uiProductListReducer.reduce(
            store = viewModel.store
        ).map { uiProducts ->
            uiProducts.filter { it.isInCart }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { uiProducts ->
            val viewState = if (uiProducts.isEmpty()) {
                UiState.Empty
            } else {
                UiState.NonEmpty(uiProducts)
            }
            epoxyController.setData(viewState)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    sealed interface UiState {
        object Empty : UiState
        data class NonEmpty(val products: List<UiProduct>) : UiState
    }
}