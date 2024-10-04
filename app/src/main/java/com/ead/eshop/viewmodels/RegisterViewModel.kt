package com.ead.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.RegisterResponse
import com.ead.eshop.data.repository.AuthRepository
import com.ead.eshop.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _registerResponse = MutableLiveData<Resource<RegisterResponse>>()
    val registerResponse: LiveData<Resource<RegisterResponse>> get() = _registerResponse

    fun registerUser(email: String, password: String) {
        _registerResponse.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = authRepository.registerUser(email ,password)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _registerResponse.value = Resource.Success(it)
                    } ?: run {
                        _registerResponse.value = Resource.Error("Unknown error occurred.")
                    }
                } else {
                    _registerResponse.value = Resource.Error("User Creation Failed")
                }
            } catch (e: HttpException) {
                _registerResponse.value = Resource.Error("Server error: ${e.message()}")
            } catch (e: IOException) {
                _registerResponse.value = Resource.Error("Network error: Please check your internet connection.")
            } catch (e: Exception) {
                _registerResponse.value = Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

}
