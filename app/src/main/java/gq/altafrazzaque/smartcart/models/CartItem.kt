package gq.altafrazzaque.smartcart.models

import com.google.gson.Gson

private val gson = Gson()
class CartItem(var product: Product, var quantity:Int){

    public fun toJson() = gson.toJson(this)

    companion object {
        public fun fromJson(json: String) = gson.fromJson(json, CartItem::class.java)
    }
}