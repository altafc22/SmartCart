package gq.altafrazzaque.smartcart.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import gq.altafrazzaque.smartcart.common.MAX_PRODUCT_SELECTION
import gq.altafrazzaque.smartcart.models.CartItem
import gq.altafrazzaque.smartcart.models.Product
import java.util.*
import kotlin.collections.ArrayList

class CartRepository {
    private val mutableCart = MutableLiveData<ArrayList<CartItem>>()
    private val mutableTotalPrice = MutableLiveData<Double>()

    fun initCart() {
        mutableCart.value = ArrayList()
        calculateCartTotal()
    }

    private fun calculateCartTotal() {
        if (mutableCart.value == null) return
        var total = 0.0
        val cartItemList = mutableCart.value
        for (cartItem in cartItemList!!) {
            total += cartItem.product.price * cartItem.quantity
        }
        mutableTotalPrice.value = total
    }

    fun addItemToCart(product: Product): Boolean {
        if (mutableCart.value == null) {
            initCart()
        }
        val cartItemList: ArrayList<CartItem> = ArrayList(mutableCart.value)
        for (cartItem in cartItemList) {
            if (cartItem!!.product.id == product.id) {
                if (cartItem.quantity == MAX_PRODUCT_SELECTION) {
                    return false
                }
                val index = cartItemList.indexOf(cartItem)
                cartItem.quantity = cartItem.quantity + 1
                cartItemList[index] = cartItem
                mutableCart.value = cartItemList
                calculateCartTotal()
                return true
            }
        }
        val cartItem = CartItem(product, 1)
        cartItemList.add(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
        return true
    }

    fun getCart(): LiveData<ArrayList<CartItem>> {
        if (mutableCart.value == null) {
            initCart()
        }
        return mutableCart
    }

    fun getTotalPrice(): LiveData<Double> {
        if (mutableTotalPrice.value == null) {
            mutableTotalPrice.value = 0.0
        }
        return mutableTotalPrice
    }

    fun removeItemFromCart(cartItem: CartItem?) {
        if (mutableCart.value == null) {
            return
        }
        val cartItemList: ArrayList<CartItem> = ArrayList(mutableCart.value)
        cartItemList.remove(cartItem)
        mutableCart.value = cartItemList
        calculateCartTotal()
    }

    fun changeQuantity(cartItem: CartItem, quantity: Int) {
        if (mutableCart.value == null) return
        val cartItemList: ArrayList<CartItem> = ArrayList(mutableCart.value)
        val updatedItem = CartItem(cartItem.product, quantity)
        cartItemList[cartItemList.indexOf(cartItem)] = updatedItem
        mutableCart.value = cartItemList
        calculateCartTotal()
    }

}