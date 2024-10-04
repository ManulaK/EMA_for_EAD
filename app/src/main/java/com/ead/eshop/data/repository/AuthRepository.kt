package com.ead.eshop.data.repository

import com.ead.eshop.data.api.RetrofitInstance
import com.ead.eshop.data.model.AuthRequest
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.model.RegisterResponse
import retrofit2.Response

class AuthRepository {
    suspend fun loginUser(username: String, password: String): Response<LoginResponse> {
        return RetrofitInstance.api.login(AuthRequest(username, password))
    }

    suspend fun registerUser(email: String, password: String): Response<RegisterResponse> {
        return RetrofitInstance.api.register(AuthRequest(email, password))
    }
}
