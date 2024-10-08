package com.ead.eshop.data.api

import com.ead.eshop.data.model.AuthRequest
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.model.Product
import com.ead.eshop.data.model.RegisterRequest
import com.ead.eshop.data.model.RegisterResponse
import com.ead.eshop.data.model.UserDetailsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @POST("user/login")
    suspend fun login(
        @Body loginRequest: AuthRequest
    ): Response<LoginResponse>


    @POST("user/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @GET("user/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): Response<UserDetailsResponse>

    @GET("products")
    suspend fun getAllProducts(): Response<List<Product>>

    @GET("products/categories")
    suspend fun getCategories(): Response<List<String>>
}
