package gq.altafrazzaque.smartcart.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import gq.altafrazzaque.smartcart.R
import gq.altafrazzaque.smartcart.viewmodel.ProductViewModel
import kotlinx.android.synthetic.main.fragment_order.view.*


class OrderFragment : Fragment() {

    lateinit var productViewModel: ProductViewModel
    lateinit var fragmentView :View
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view
        initViews()
    }

    private fun initViews() {
        productViewModel = ViewModelProvider(requireActivity()).get(ProductViewModel::class.java)
        navController = Navigation.findNavController(fragmentView)

        fragmentView.btnShopping.setOnClickListener{
            productViewModel.clearCart()
            navController.navigate(R.id.action_orderFragment_to_productListFragment)
        }
    }
}