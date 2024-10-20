package com.ead.eshop.data.repository

import com.ead.eshop.data.api.RetrofitInstance
import com.ead.eshop.data.model.AddToCartRequest
import com.ead.eshop.data.model.AddToCartResponse
import com.ead.eshop.data.model.Cart
import com.ead.eshop.data.model.Category
import com.ead.eshop.data.model.Product

import retrofit2.Response

class ProductRepository {
    suspend fun getAllProducts(token: String): Response<List<Product>> {
        return RetrofitInstance.api.getAllProducts(token)
    }

    suspend fun getCategories(token :String): Response<List<Category>> {
        return RetrofitInstance.api.getCategories(token)
    }

    suspend fun addToCart(token: String, addToCartRequest: AddToCartRequest): Response<AddToCartResponse> {
        return RetrofitInstance.api.addToCart(token,addToCartRequest)
    }
    suspend fun getCart(token: String): Response<Cart> {
        return RetrofitInstance.api.getCart(token)
    }
}

