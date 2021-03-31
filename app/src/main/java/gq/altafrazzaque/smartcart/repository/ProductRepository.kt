package gq.altafrazzaque.smartcart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gq.altafrazzaque.smartcart.models.Product
import gq.altafrazzaque.smartcart.network.APIService
import gq.altafrazzaque.smartcart.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProductRepository {
    private var productList : MutableLiveData<ArrayList<Product>?> = MutableLiveData<ArrayList<Product>?>()

    fun getProducts(): LiveData<ArrayList<Product>?> {
        val apiService: APIService = ApiClient.apiClient!!.create(APIService::class.java)
        val call: Call<ArrayList<Product>> = apiService.getProducts()
        call.enqueue(object : Callback<ArrayList<Product>> {
            override fun onResponse(
                    call: Call<ArrayList<Product>>,
                    response: Response<ArrayList<Product>>
            ) {
                println(response.body())
                productList.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<Product>>?, t: Throwable?) {
                productList.postValue(null)
            }
        })
        return productList
    }

}