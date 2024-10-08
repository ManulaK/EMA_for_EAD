package com.ead.eshop.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.UserDetailsResponse
import com.ead.eshop.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var userDetails = mutableStateOf<UserDetailsResponse?>(null)
        private set

    fun loadUserData(token: String) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = authRepository.getUser(bearerToken)
                if (response.isSuccessful) {
                    userDetails.value = response.body()
                } else {
                    // Handle error (e.g., show a Toast or log)
                }
            } catch (e: Exception) {
                // Handle exception (e.g., show a Toast or log)
            }
        }
    }
}