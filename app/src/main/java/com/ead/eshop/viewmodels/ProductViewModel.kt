package com.ead.eshop.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ead.eshop.data.model.Product
import com.ead.eshop.data.repository.ProductRepository
import com.ead.eshop.utils.Resource

import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> get() = _products

    private val _categories = MutableLiveData<Resource<List<String>>>()
    val categories: LiveData<Resource<List<String>>> get() = _categories

    fun fetchProducts() {
        _products.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = productRepository.getAllProducts()
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

    fun fetchCategories() {
        _categories.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = productRepository.getCategories()
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
