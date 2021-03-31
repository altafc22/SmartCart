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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.adapters.CartAdapter
import gq.altafrazzaque.smartcart.common.MAX_PRODUCT_SELECTION
import gq.altafrazzaque.smartcart.models.CartItem
import gq.altafrazzaque.smartcart.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_cart.view.*

class CartFragment : Fragment(), CartAdapter.OnCartItemListener {
    private val TAG = "CartFragment"
    lateinit var productViewModel: ProductViewModel
    lateinit var fragmentView :View
    private lateinit var navController: NavController

    private lateinit var cartListAdapter: CartAdapter
    private lateinit var cartRecyclerView: RecyclerView

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
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

        cartRecyclerView = fragmentView.cartRecyclerView

        productViewModel.getCart().observe(viewLifecycleOwner, { cartItems ->

            cartListAdapter = CartAdapter(cartItems!!, requireActivity().baseContext, this)
            cartRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            cartRecyclerView.adapter = cartListAdapter

            cartRecyclerView.adapter!!.notifyDataSetChanged()
            fragmentView.btnPlaceOrder.isEnabled = cartItems.isNotEmpty()
        })

        productViewModel.getTotalPrice().observe(viewLifecycleOwner, { aDouble -> fragmentView.tvOrderTotal.text = "Total: $aDouble AED" })

        fragmentView.btnPlaceOrder.setOnClickListener {
            if(fragmentView.cbCod.isChecked)
            {
                navController.navigate(R.id.action_cartFragment_to_orderFragment)
                productViewModel.clearCart()
            } else {
                Snackbar.make(
                    requireView(),
                    "Please select payment mode.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    override fun updateQuantity(cartItem: CartItem, quantity: Int, isAdd:Boolean) {
        if(isAdd)
        {
            if(quantity< MAX_PRODUCT_SELECTION)
                productViewModel.changeQuantity(cartItem, quantity+1)
            else
                Snackbar.make(
                        requireView(),
                        "Maximum $MAX_PRODUCT_SELECTION product allowed.",
                        Snackbar.LENGTH_SHORT
                ).show()
        }
        else
        {
            if(quantity>1)
                productViewModel.changeQuantity(cartItem, quantity-1)
            else
            {
                productViewModel.removeItemFromCart(cartItem)
                cartRecyclerView.adapter!!.notifyDataSetChanged()
            }
        }
        Log.i(TAG,"QTY: $quantity")
    }
    override fun removeCartItem(cartItem: CartItem) {
        productViewModel.removeItemFromCart(cartItem)
        cartRecyclerView.adapter!!.notifyDataSetChanged()
    }

}