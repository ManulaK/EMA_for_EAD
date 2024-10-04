package com.ead.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.repository.AuthRepository
import com.ead.eshop.utils.Resource
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class LoginViewModel : ViewModel() {

    private val authRepository = AuthRepository()

    private val _loginResponse = MutableLiveData<Resource<LoginResponse>>()
    val loginResponse: LiveData<Resource<LoginResponse>> get() = _loginResponse

    fun loginUser(username: String, password: String) {
        _loginResponse.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = authRepository.loginUser(username, password)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _loginResponse.value = Resource.Success(it)
                    } ?: run {
                        _loginResponse.value = Resource.Error("Unknown error occurred.")
                    }
                } else {
                    _loginResponse.value = Resource.Error("Login failed. Check your credentials.")
                }
            } catch (e: HttpException) {
                _loginResponse.value = Resource.Error("Server error: ${e.message()}")
            } catch (e: IOException) {
                _loginResponse.value = Resource.Error("Network error: Please check your internet connection.")
            } catch (e: Exception) {
                _loginResponse.value = Resource.Error("Unexpected error: ${e.localizedMessage}")
            }
        }
    }

}
