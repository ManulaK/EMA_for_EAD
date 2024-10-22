package com.ead.eshop.viewmodels

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ead.eshop.AppRoutes
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
import com.ead.eshop.data.repository.ProductRepository
import com.ead.eshop.utils.Resource

import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<Resource<List<Product>>>()
    val products: LiveData<Resource<List<Product>>> get() = _products

    private val _product = MutableLiveData<Resource<ProductByIdResponse>>()
    val product: LiveData<Resource<ProductByIdResponse>> get() = _product

    private val _categories = MutableLiveData<Resource<List<Category>>>()
    val categories: LiveData<Resource<List<Category>>> get() = _categories

    private val _cartItems = MutableLiveData<Resource<Cart>>()
    val cartItems: LiveData<Resource<Cart>> = _cartItems

    private val _orders = MutableLiveData <Resource<List<OrderResponse>>>()
    val orders: LiveData<Resource<List<OrderResponse>>> = _orders

    private var addToCartStatus = mutableStateOf<AddToCartResponse?>(null)

    private var createOrderStatus = mutableStateOf<OrderResponse?>(null)

    private var rateVendorStatus = mutableStateOf<RateVendorResponse?>(null)

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

    fun fetchProductById(token: String , id: String) {
        _product.value = Resource.Loading()
        val bearerToken = "Bearer $token"

        viewModelScope.launch {
            try {
                val response = productRepository.getProductById(bearerToken, id)
                if (response.isSuccessful) {
                    response.body()?.let {
                        _product.value = Resource.Success(it)
                    } ?: run {
                        _product.value = Resource.Error("Failed to load product.")
                    }
                } else {
                    _product.value = Resource.Error("Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                _product.value = Resource.Error("Error: ${e.localizedMessage}")
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

    fun addToCart(token: String, addToCartRequest: AddToCartRequest, context: Context, navController: NavController) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = productRepository.addToCart(bearerToken, addToCartRequest)
                if (response.isSuccessful) {
                    addToCartStatus.value = response.body()
                    Toast.makeText(context, "Product added to cart successfully!", Toast.LENGTH_SHORT).show()

                    // Navigate to the cart screen upon success
                    navController.navigate(AppRoutes.cartScreen)

                } else {
                    Toast.makeText(context, "Failed to add product. Please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

                    Log.e("AddToCartError", "Error adding to cart: ${e.message}", e)
                    Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    fun fetchCart(token: String) {
        val bearerToken = "Bearer $token"
        _cartItems.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = productRepository.getCart(bearerToken)
                if (response.isSuccessful) {
                    val cart = response.body()

                    cart?.let {
                        // If cart is not null, update the state with fetched data
                        _cartItems.value = Resource.Success(it)
                    } ?: run {
                        // If cart is null, update with an error message
                        _cartItems.value = Resource.Error("Cart data is null.")
                    }
                } else {
                    // Handle the case where the response is not successful
                    _cartItems.value = Resource.Error("Failed to load cart. Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exceptions like network errors or JSON parsing issues
                _cartItems.value = Resource.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun fetchOrders(token: String) {
        val bearerToken = "Bearer $token"
        _orders.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val response = productRepository.getMyOrders(bearerToken)
                if (response.isSuccessful) {
                    val cart = response.body()

                    cart?.let {
                        // If cart is not null, update the state with fetched data
                        _orders.value = Resource.Success(it)
                    } ?: run {
                        // If cart is null, update with an error message
                        _orders.value = Resource.Error("Order data is null.")
                    }
                } else {
                    // Handle the case where the response is not successful
                    _orders.value = Resource.Error("Failed to load orders. Error code: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exceptions like network errors or JSON parsing issues
                _orders.value = Resource.Error("An error occurred: ${e.localizedMessage}")
            }
        }
    }

    fun makeOrder(token: String, orderRequest: OrderRequest, context: Context, navController: NavController) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = productRepository.createOrder(bearerToken, orderRequest)
                if (response.isSuccessful) {
                    createOrderStatus.value = response.body()
                    Toast.makeText(context, "Order placed successfully!", Toast.LENGTH_SHORT).show()

                    // Navigate to the cart screen upon success
                    navController.navigate(AppRoutes.cartScreen)

                } else {
                    Toast.makeText(context, "Failed to placed order, Please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {

                Log.e("CreateOrderResponse", "Error adding to cart: ${e.message}", e)
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun rateVendor(token: String, rateVendorRequest: RateVendorRequest, id: String, context: Context) {
        val bearerToken = "Bearer $token"
        viewModelScope.launch {
            try {
                val response = productRepository.rateVendor(bearerToken, rateVendorRequest, id)
                if (response.isSuccessful) {
                    rateVendorStatus.value = response.body()
                    Toast.makeText(context, "Vendor rated successfully!", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Failed to placed order, Please try again.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(context, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
