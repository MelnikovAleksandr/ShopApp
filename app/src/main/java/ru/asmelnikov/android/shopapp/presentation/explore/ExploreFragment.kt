package ru.asmelnikov.android.shopapp.presentation.explore

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.distinctUntilChanged
import dagger.hilt.android.AndroidEntryPoint
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.FragmentExploreBinding

@AndroidEntryPoint
class ExploreFragment : Fragment(R.layout.fragment_explore) {

    private var _binding: FragmentExploreBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel by viewModels<ExploreViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentExploreBinding.bind(view)

        val exploreFragmentActions = ExploreFragmentActions(viewModel)
        val epoxyController = ExploreFragmentEpoxyController(exploreFragmentActions)
        binding.epoxyRecyclerView.setController(epoxyController)

        viewModel.uiViewState
            .asLiveData()
            .distinctUntilChanged()
            .observe(viewLifecycleOwner) { viewState ->
                epoxyController.setData(viewState)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}