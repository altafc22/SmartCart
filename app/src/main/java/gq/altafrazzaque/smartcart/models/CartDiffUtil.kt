package gq.altafrazzaque.smartcart.models

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class CartDiffUtil(newCart: ArrayList<CartItem>, oldCart: ArrayList<CartItem>) :
    DiffUtil.Callback() {

    var oldCart: ArrayList<CartItem> = oldCart
    var newCart: ArrayList<CartItem> = newCart

    override fun getOldListSize(): Int {
        return oldCart.size
    }

    override fun getNewListSize(): Int {
        return newCart.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCart[oldItemPosition].product.id === newCart[newItemPosition].product.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCart[oldItemPosition] == newCart[newItemPosition]
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}