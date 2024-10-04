package com.ead.eshop.data.api

import com.ead.eshop.data.model.AuthRequest
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.model.RegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: AuthRequest
    ): Response<LoginResponse>


    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: AuthRequest
    ): Response<RegisterResponse>
}
