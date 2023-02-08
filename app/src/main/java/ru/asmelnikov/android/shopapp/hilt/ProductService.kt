package ru.asmelnikov.android.shopapp.hilt

import retrofit2.Response
import retrofit2.http.GET
import ru.asmelnikov.android.shopapp.models.network.NetworkProduct

interface ProductService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}