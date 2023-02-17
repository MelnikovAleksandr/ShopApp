package ru.asmelnikov.android.shopapp.presentation.profile

import android.util.Log
import ru.asmelnikov.android.shopapp.R
import ru.asmelnikov.android.shopapp.presentation.profile.auth.AuthViewModel

class ProfileUiAction(private val viewModel: AuthViewModel) {

    fun onSignIn(userName: String, password: String) {
        viewModel.login(userName, password)
    }

    fun onProfileItemSelected(id: Int) {
        when (id) {
            R.drawable.ic_round_phone_24 -> {}
            R.drawable.ic_round_location_on_24 -> {}
            R.drawable.ic_round_logout_24 -> viewModel.logout()
            else -> {
                Log.i("SELECTION", "Unknown ID -> $id")
            }
        }
    }
}