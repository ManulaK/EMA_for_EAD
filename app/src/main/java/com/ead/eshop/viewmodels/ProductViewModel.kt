package com.ead.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.Category
import com.ead.eshop.data.model.Product
import com.ead.eshop.data.repository.ProductRepository
import com.ead.eshop.utils.Resource

import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> get() = _products

    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    fun fetchProducts(token: String) {
        _products.value = Resource.Loading()
        val bearerToken = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = productRepository.getAllProducts(bearerToken)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _products.value = Resource.Success(it)
                    } ?: run {
                        _products.value = Resource.Error("Failed to load products.")
                    }
                } else {
                    _products.value = Resource.Error("Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                _products.value = Resource.Error("Error: ${e.localizedMessage}")
            }
        }
    }

    fun fetchCategories(token :String) {
        val bearerToken = "Bearer $token"
        _categories.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = productRepository.getCategories(bearerToken)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _categories.value = Resource.Success(it)
                    } ?: run {
                        _categories.value = Resource.Error("Failed to load categories.")
                    }
                } else {
                    _categories.value = Resource.Error("Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                _categories.value = Resource.Error("Error: ${e.localizedMessage}")
            }
        }
    }
}
