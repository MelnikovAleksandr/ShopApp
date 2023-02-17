package ru.asmelnikov.android.shopapp.presentation.profile

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
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.databinding.FragmentProfileBinding
import ru.asmelnikov.android.shopapp.presentation.profile.auth.AuthViewModel
import ru.asmelnikov.android.shopapp.utils.ResourceProvider
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var profilerItemGenerator: UserProfilerItemGenerator

    @Inject
    lateinit var resourceProvider: ResourceProvider

    private val authViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val uiAction = ProfileUiAction(authViewModel)
        val epoxyController =
            ProfileEpoxyController(profilerItemGenerator, uiAction, resourceProvider)
        binding.epoxyRecyclerView.setController(epoxyController)


        authViewModel.store.stateFlow.map {
            it.user
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner) { user ->

            epoxyController.setData(user)

            binding.headerTextView.text = if (user?.name?.firstname == null) {
                getString(R.string.sign_in)
            } else {
                getString(R.string.welcome_message, user.name.firstname)
            }
            binding.infoTextView.text = user?.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
