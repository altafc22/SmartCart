package gq.altafrazzaque.smartcart.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.models.Product
import gq.altafrazzaque.smartcart.models.ProductDiffUtil


class ProductListAdapter(
    private var list: ArrayList<Product>,
    var context: Context,
    var productClickListener: OnProductClickListener
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder?>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.btnAddToCart.setOnClickListener {
            productClickListener.addProduct(item)
        }
        holder.productCard.setOnClickListener {
            productClickListener.onProductItemClick(item)
        }
        holder.tvName.text = item.title
        holder.tvDetails.text = item.description
        holder.tvPrice.text = "${item.price} AED"
        holder.tvCategory.text = item.category

        val options: RequestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.product_placeholder)
            .error(R.drawable.product_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
        Glide.with(this.context).load(list[position].image)
            .apply(options)
            .into(holder.productImageView)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var btnAddToCart : Button = view.findViewById(R.id.btnAddToCart)
        var tvName : TextView = view.findViewById(R.id.tvName)
        var tvDetails : TextView = view.findViewById(R.id.tvDetails)
        var tvPrice : TextView = view.findViewById(R.id.tvPrice)
        var tvCategory : TextView = view.findViewById(R.id.tvCategory)
        var productImageView : ImageView = view.findViewById(R.id.productImageView)
        var productCard : CardView = view.findViewById(R.id.productCard)
    }

    interface OnProductClickListener {
        fun addProduct(product: Product?)
        fun onProductItemClick(product: Product?)
    }

    fun updateList(newList: ArrayList<Product>) {
        val diffResult = DiffUtil.calculateDiff(ProductDiffUtil(this.list, newList))
        diffResult.dispatchUpdatesTo(this)
    }
}
