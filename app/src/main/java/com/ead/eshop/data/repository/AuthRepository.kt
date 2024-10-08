package com.ead.eshop.data.repository

import com.ead.eshop.data.api.RetrofitInstance
import com.ead.eshop.data.model.Address
import com.ead.eshop.data.model.AuthRequest
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.model.RegisterRequest
import com.ead.eshop.data.model.RegisterResponse
import com.ead.eshop.data.model.UserDetailsResponse
import retrofit2.Response

class AuthRepository {
    suspend fun loginUser(email: String, password: String): Response<LoginResponse> {
        return RetrofitInstance.api.login(AuthRequest(email, password))
    }

    suspend fun registerUser(
        username: String,
        passwordHash: String,
        email: String,
        firstName: String,
        lastName: String,
        dateOfBirth: String,
        street: String,
        city: String,
        state: String,
        postalCode: String,
        country: String
    ): Response<RegisterResponse> {
        val address = Address(street, city, state, postalCode, country)
        return RetrofitInstance.api.register(
            RegisterRequest(
                username = username,
                passwordHash = passwordHash,
                role = "Customer",
                email = email,
                firstName = firstName,
                lastName = lastName,
                dateOfBirth = dateOfBirth,
                address = address
            )
        )
    }

    suspend fun getUser( token: String): Response<UserDetailsResponse> {
        return RetrofitInstance.api.getMe(token)
    }
}
