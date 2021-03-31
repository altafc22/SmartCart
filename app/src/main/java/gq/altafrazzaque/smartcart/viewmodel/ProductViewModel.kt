package gq.altafrazzaque.smartcart.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import gq.altafrazzaque.smartcart.models.CartItem
import gq.altafrazzaque.smartcart.models.Product
import gq.altafrazzaque.smartcart.repository.CartRepository
import gq.altafrazzaque.smartcart.repository.ProductRepository


class ProductViewModel : ViewModel() {

    private var productRepo = ProductRepository()
    private var cartRepo = CartRepository()

    var mutableProduct = MutableLiveData<Product>()

    fun getProducts(): LiveData<ArrayList<Product>?> {
        return productRepo.getProducts()
    }

    fun setProduct(product: Product?) {
        mutableProduct.value = product!!
    }

    fun getProduct(): LiveData<Product?> {
        return mutableProduct
    }

    fun getCart(): LiveData<ArrayList<CartItem>>  {
        return cartRepo.getCart()
    }

    fun addItemToCart(product: Product): Boolean {
        return cartRepo.addItemToCart(product)
    }

    fun removeItemFromCart(cartItem: CartItem?) {
        cartRepo.removeItemFromCart(cartItem)
    }

    fun changeQuantity(cartItem: CartItem, quantity: Int) {
        cartRepo.changeQuantity(cartItem!!, quantity)
    }

    fun getTotalPrice(): LiveData<Double> {
        return cartRepo.getTotalPrice()
    }

    fun clearCart() {
        cartRepo.initCart()
    }


}