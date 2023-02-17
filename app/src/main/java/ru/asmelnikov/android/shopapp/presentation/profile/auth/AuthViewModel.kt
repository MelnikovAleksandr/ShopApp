package ru.asmelnikov.android.shopapp.presentation.profile.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Response
import ru.asmelnikov.android.shopapp.models.mapper.UserMapper
import ru.asmelnikov.android.shopapp.models.network.LoginResponse
import ru.asmelnikov.android.shopapp.models.network.NetworkUser
import ru.asmelnikov.android.shopapp.redux.ApplicationState
import ru.asmelnikov.android.shopapp.redux.Store
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository,
    private val userMapper: UserMapper
) : ViewModel() {

    private fun ResponseBody?.parseError(): String? {
        return this?.byteStream()?.bufferedReader()?.readLine()?.capitalize()
    }

    fun login(username: String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)
        if (response.isSuccessful) {
            val donUserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update { applicationState ->
                val authState = donUserResponse.body()?.let { body ->
                    ApplicationState.AuthState.Auth(user = userMapper.buildFrom(body))
                } ?: ApplicationState.AuthState.UnAuth(
                    errorString = response.errorBody()?.parseError()
                )
                return@update applicationState.copy(authState = authState)
            }

        } else {
            store.update { applicationState ->
                applicationState.copy(
                    authState = ApplicationState.AuthState.UnAuth(
                        errorString = response.errorBody()?.parseError()
                    )
                )
            }
        }
    }

    fun logout() = viewModelScope.launch {
        store.update { applicationState ->
            applicationState.copy(authState = ApplicationState.AuthState.UnAuth())
        }
    }

    private fun String.capitalize(): String {
        return this.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }
}