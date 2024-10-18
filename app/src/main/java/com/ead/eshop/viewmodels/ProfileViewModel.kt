package com.ead.eshop.viewmodels

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.UpdateRequest
import com.ead.eshop.data.model.UserDeactivateResponse
import com.ead.eshop.data.model.UserDetailsResponse
import com.ead.eshop.data.model.UserUpdateResponse
import com.ead.eshop.data.repository.AuthRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val authRepository: AuthRepository) : ViewModel() {
    var userDetails = mutableStateOf<UserDetailsResponse?>(null)
        private set

    private var updateStatus = mutableStateOf<UserUpdateResponse?>(null)
    private var deactivateStatus = mutableStateOf<UserDeactivateResponse?>(null)
    var isDeactivated = mutableStateOf(false)

    fun loadUserData(token: String) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = authRepository.getUser(bearerToken)
                if (response.isSuccessful) {
                    userDetails.value = response.body()
                }
            } catch (e: Exception) {
                // Handle exception (e.g., show a Toast or log)
            }
        }
    }

    fun updateUserData(token: String, updateRequest: UpdateRequest, context: Context) {
        viewModelScope.launch {
            try {
                val response = authRepository.updateMe(token, updateRequest)
                if (response.isSuccessful) {
                    updateStatus.value = response.body()
                    Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Update failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deactivateUser(token: String, context: Context) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = authRepository.deactivateUser(bearerToken)
                if (response.isSuccessful) {
                    deactivateStatus.value = response.body()
                    Toast.makeText(context, "Account successfully deactivated!", Toast.LENGTH_SHORT).show()
                    isDeactivated.value = true
                } else {
                    Toast.makeText(context, "Deactivation failed. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "An error occurred. Please try again later.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}