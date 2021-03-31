package gq.altafrazzaque.smartcart.models

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

class ProductDiffUtil(newPersons: ArrayList<Product>, oldPersons: ArrayList<Product>) :
    DiffUtil.Callback() {

    var oldProducts: ArrayList<Product> = oldPersons
    var newProducts: ArrayList<Product> = newPersons

    override fun getOldListSize(): Int {
        return oldProducts.size
    }

    override fun getNewListSize(): Int {
        return newProducts.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldProducts[oldItemPosition].id === newProducts[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldProducts[oldItemPosition].equals(newProducts[newItemPosition])
    }

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}