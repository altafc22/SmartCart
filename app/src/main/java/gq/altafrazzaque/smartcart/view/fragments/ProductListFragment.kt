package gq.altafrazzaque.smartcart.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.adapters.ProductListAdapter
import gq.altafrazzaque.smartcart.models.Product
import gq.altafrazzaque.smartcart.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_product_list.view.*
import java.lang.NullPointerException

class ProductListFragment : Fragment() , ProductListAdapter.OnProductClickListener {
    private val TAG = "ProductListFragment"
    private lateinit var productViewModel: ProductViewModel
    private lateinit var navController: NavController

    private lateinit var productList : ArrayList<Product>
    private lateinit var productListAdapter: ProductListAdapter
    private lateinit var productRecyclerView: RecyclerView

    private var fragmentView:View? = null
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_product_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        initViews()
    }

    private fun initViews()
    {
        productList = ArrayList()
        productRecyclerView = fragmentView!!.productRecyclerView
        productListAdapter = ProductListAdapter(productList, requireActivity().baseContext, this)
        productRecyclerView.layoutManager= GridLayoutManager(activity, 2)
        fragmentView!!.productRecyclerView.adapter= productListAdapter

        productViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        productViewModel.getProducts().observe(viewLifecycleOwner,
                { products ->
                   try {
                       productList.addAll(products!!)
                       productRecyclerView.adapter!!.notifyDataSetChanged()
                   } catch (ex:NullPointerException)
                   {
                       Log.i(TAG,ex.message.toString())
                   }
                })

        navController = Navigation.findNavController(fragmentView!!)
    }

    override fun addProduct(product: Product?) {

        val isAdded: Boolean = productViewModel.addItemToCart(product!!)
        if (isAdded) {
            Snackbar.make(requireView(), product.title + " added to cart.", Snackbar.LENGTH_SHORT)
                    //.setAction("Checkout") { navController.navigate(R.id.action_productListFragment_to_cartFragment) }
                    .show()
        } else {
            Snackbar.make(
                    requireView(),
                    "Already have the max quantity in cart.",
                    Snackbar.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onProductItemClick(product: Product?) {
        productViewModel.setProduct(product)
        navController.navigate(R.id.action_productListFragment_to_productDetailFragment)
    }

}