package gq.altafrazzaque.smartcart.network

import gq.altafrazzaque.smartcart.models.Product
import retrofit2.Call
import retrofit2.http.GET


interface APIService {
    @GET("products")
    fun getProducts(): Call<ArrayList<Product>>
}