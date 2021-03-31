package gq.altafrazzaque.smartcart.models

import androidx.recyclerview.widget.DiffUtil
import com.google.gson.Gson

private val gson = Gson()
class Product(
    var id: Long,
    var title: String,
    var price: Double,
    var description: String,
    var category: String,
    var image: String
) {
    public fun toJson() = gson.toJson(this)

    companion object {
        public fun fromJson(json: String) = gson.fromJson(json, Product::class.java)
    }
}
