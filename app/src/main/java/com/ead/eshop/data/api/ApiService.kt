package com.ead.eshop.data.api

import com.ead.eshop.data.model.AddToCartRequest
import com.ead.eshop.data.model.AddToCartResponse
import com.ead.eshop.data.model.AuthRequest
import com.ead.eshop.data.model.Cart
import com.ead.eshop.data.model.Category
import com.ead.eshop.data.model.LoginResponse
import com.ead.eshop.data.model.OrderRequest
import com.ead.eshop.data.model.OrderResponse
import com.ead.eshop.data.model.Product
import com.ead.eshop.data.model.ProductByIdResponse
import com.ead.eshop.data.model.RateVendorRequest
import com.ead.eshop.data.model.RateVendorResponse
import com.ead.eshop.data.model.RegisterRequest
import com.ead.eshop.data.model.RegisterResponse
import com.ead.eshop.data.model.UpdateRequest
import com.ead.eshop.data.model.UserDeactivateResponse
import com.ead.eshop.data.model.UserDetailsResponse
import com.ead.eshop.data.model.UserUpdateResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("user/login")
    suspend fun login(
        @Body loginRequest: AuthRequest
    ): Response<LoginResponse>

    @POST("user/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<RegisterResponse>

    @GET("user/me")
    suspend fun getMe(
        @Header("Authorization") token: String
    ): Response<UserDetailsResponse>

    @PUT("user/update")
    suspend fun updateMe(
        @Header("Authorization") token: String,
        @Body updateRequest: UpdateRequest
    ): Response<UserUpdateResponse>

    @PUT("user/deactivate")
    suspend fun deactivateMe(
        @Header("Authorization") token: String
    ): Response<UserDeactivateResponse>

    @POST("user/{id}/rate")
    suspend fun rateVendor(
        @Header("Authorization") token: String,
        @Body rateVendorRequest: RateVendorRequest,
        @Path("id") id: String
    ): Response<RateVendorResponse>

    @GET("product/all")
    suspend fun getAllProducts(
        @Header("Authorization") token: String
    ): Response<List<Product>>

    @GET("product/singleProduct/{id}")
    suspend fun getProductsById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<ProductByIdResponse>

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<List<Category>>

    @POST("cart/add")
    suspend fun addToCart(
        @Header("Authorization") token: String,
        @Body addToCartRequest: AddToCartRequest
    ): Response<AddToCartResponse>

    @GET("cart")
    suspend fun getCart(
        @Header("Authorization") token: String
    ): Response<Cart>

    @POST("order/create")
    suspend fun createOrder(
        @Header("Authorization") token: String,
        @Body orderRequest: OrderRequest
    ): Response<OrderResponse>

    @GET("order/my-orders")
    suspend fun getMyOrders(
        @Header("Authorization") token: String
    ): Response<List<OrderResponse>>
}
