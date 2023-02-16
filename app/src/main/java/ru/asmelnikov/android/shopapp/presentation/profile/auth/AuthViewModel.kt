package ru.asmelnikov.android.shopapp.presentation.profile.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import ru.asmelnikov.android.shopapp.models.network.LoginResponse
import ru.asmelnikov.android.shopapp.models.network.NetworkUser
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import ru.asmelnikov.android.shopapp.redux.Store
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository
) : ViewModel() {
    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update {
                it.copy(user = donUserResponse.body())
            }
            if (donUserResponse.body() == null) {
                Log.e("LOGIN", response.errorBody()?.toString() ?: response.message())
            }
        } else {
            withContext(Dispatchers.IO) {
                Log.d(
                    "LOGIN",
                    response.errorBody()?.byteStream()?.bufferedReader()?.readLine()
                        ?: "Invalid login"
                )
            }
        }
    }
}