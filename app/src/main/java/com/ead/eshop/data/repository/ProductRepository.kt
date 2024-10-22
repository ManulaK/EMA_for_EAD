package com.ead.eshop.data.repository

import com.ead.eshop.data.api.RetrofitInstance
import com.ead.eshop.data.model.AddToCartRequest
import com.ead.eshop.data.model.AddToCartResponse
import com.ead.eshop.data.model.Cart
import com.ead.eshop.data.model.Category
import com.ead.eshop.data.model.OrderRequest
import com.ead.eshop.data.model.OrderResponse
import com.ead.eshop.data.model.Product
import com.ead.eshop.data.model.ProductByIdResponse
import com.ead.eshop.data.model.RateVendorRequest
import com.ead.eshop.data.model.RateVendorResponse

import retrofit2.Response

class ProductRepository {
    suspend fun getAllProducts(token: String): Response<List<Product>> {
        return RetrofitInstance.api.getAllProducts(token)
    }

    suspend fun getProductById(token: String , id:String): Response<ProductByIdResponse> {
        return RetrofitInstance.api.getProductsById(token, id)
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

    suspend fun createOrder(token: String , orderRequest: OrderRequest): Response<OrderResponse> {
        return RetrofitInstance.api.createOrder(token,orderRequest)
    }

    suspend fun getMyOrders(token: String): Response<List<OrderResponse>> {
        return RetrofitInstance.api.getMyOrders(token)
    }

    suspend fun rateVendor(token: String, rateVendorRequest: RateVendorRequest, id: String): Response<RateVendorResponse> {
        return RetrofitInstance.api.rateVendor(token,rateVendorRequest, id)
    }
}

