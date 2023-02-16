package ru.asmelnikov.android.shopapp.presentation.profile.auth

import retrofit2.Response
import ru.asmelnikov.android.shopapp.models.network.LoginResponse
import ru.asmelnikov.android.shopapp.models.network.NetworkUser
import ru.asmelnikov.android.shopapp.models.network.post.LoginPostBody
import javax.inject.Inject

class AuthRepository @Inject constructor(private val authService: AuthService) {

    suspend fun login(username: String, password: String): Response<LoginResponse> {
        return authService.login(LoginPostBody(username, password))
    }

    suspend fun fetchDon(): Response<NetworkUser> {
        return authService.fetchUser(userId = 4)
    }

}