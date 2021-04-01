package gq.altafrazzaque.smartcart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.models.Product
import gq.altafrazzaque.smartcart.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_details.view.*
import kotlinx.android.synthetic.main.fragment_product_list.view.*


class ProductDetailsFragment : Fragment() {
    lateinit var productViewModel: ProductViewModel
    lateinit var fragmentView :View
    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_details, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        initViews()
    }

    private fun initViews()
    {
        productViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        navController = Navigation.findNavController(fragmentView)
        productViewModel.getProduct()!!.observe(viewLifecycleOwner,
                { product ->
                    //product = item!!
                    setViewData(product!!)
                })

    }

    private fun setViewData(product: Product){
        fragmentView.tvProductName.text = product.title
        fragmentView.tvDetails.text = product.description
        fragmentView.tvPrice.text = "${product.price} AED"
        fragmentView.tvCategory.text = product.category

        val options: RequestOptions = RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.product_placeholder)
                .error(R.drawable.product_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
        Glide.with(this).load(product.image)
                .apply(options)
                .into(fragmentView.productImageView)

        fragmentView.btnAddToCart.setOnClickListener {
            val isAdded: Boolean = productViewModel.addItemToCart(product)
            if (isAdded) {
                Snackbar.make(requireView(), product.title + " added to cart.", Snackbar.LENGTH_LONG)
                        .setAction("Checkout") { navController.navigate(R.id.action_productDetailFragment_to_cartFragment) }
                        .show()
            } else {
                Snackbar.make(
                        requireView(),
                        "Already have the max quantity in cart.",
                        Snackbar.LENGTH_LONG
                )
                        .show()
            }
        }
    }

}