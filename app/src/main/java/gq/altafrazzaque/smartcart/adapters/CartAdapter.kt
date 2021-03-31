package gq.altafrazzaque.smartcart.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.common.MAX_PRODUCT_SELECTION
import gq.altafrazzaque.smartcart.models.CartDiffUtil
import gq.altafrazzaque.smartcart.models.CartItem


class CartAdapter(
        private var list: ArrayList<CartItem>,
        var context: Context,
        var cartItemListener: OnCartItemListener
) :
    RecyclerView.Adapter<CartAdapter.ViewHolder?>() {

    private val TAG  = "CartAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.item_product_cart, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.btnAddProduct.setOnClickListener {
            cartItemListener.updateQuantity(list[position], item.quantity,true)
        }
        holder.btnRemoveProduct.setOnClickListener {

            cartItemListener.updateQuantity(list[position], item.quantity,false)
        }
        holder.btnDeleteProduct.setOnClickListener {
                cartItemListener.removeCartItem(item)
        }

        holder.tvProductName.text = item.product.title
        holder.tvProductQuantity.text = item.quantity.toString()
        holder.tvProductTotal.text = "${item.product.price * item.quantity} AED"

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.product_placeholder)
            .error(R.drawable.product_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        Glide.with(this.context).load(item.product.image)
            .apply(options)
            .into(holder.productImageView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var productImageView : ImageView = view.findViewById(R.id.productImageView)
        var btnDeleteProduct : ImageButton = view.findViewById(R.id.btnDeleteProduct)
        var btnRemoveProduct : ImageButton = view.findViewById(R.id.btnRemoveProduct)
        var btnAddProduct : ImageButton = view.findViewById(R.id.btnAddProduct)
        var tvProductName : TextView = view.findViewById(R.id.tvProductName)
        var tvProductTotal : TextView = view.findViewById(R.id.tvProductTotal)
        var tvProductQuantity : TextView = view.findViewById(R.id.tvProductQuantity)
    }

    interface OnCartItemListener {
        fun updateQuantity(cartItem: CartItem, quantity: Int, isAdd:Boolean)
        fun removeCartItem(cartItem: CartItem)
    }

    fun updateList(newList: ArrayList<CartItem>) {
        val diffResult = DiffUtil.calculateDiff(CartDiffUtil(list, newList))
        diffResult.dispatchUpdatesTo(this)
    }
}
