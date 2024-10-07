package com.ead.eshop.data.repository

import com.ead.eshop.data.api.RetrofitInstance
import com.ead.eshop.data.model.Product

import retrofit2.Response

class ProductRepository {
    suspend fun getAllProducts(): Response<List<Product>> {
        return RetrofitInstance.api.getAllProducts()
    }

    suspend fun getCategories(): Response<List<String>> {
        return RetrofitInstance.api.getCategories()
    }
}

