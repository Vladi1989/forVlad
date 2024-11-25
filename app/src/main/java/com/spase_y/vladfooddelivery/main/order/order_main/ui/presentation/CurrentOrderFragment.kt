package com.spase_y.vladfooddelivery.main.order.order_main.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentCurrentOrderBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.MenuItem
import com.spase_y.vladfooddelivery.main.order.delivery.DeliveryFragment
import com.spase_y.vladfooddelivery.main.order.order_main.ui.adapters.OrderAdapter
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState
import com.spase_y.vladfooddelivery.main.order.order_main.ui.view_model.OrderViewModel
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import org.koin.android.ext.android.inject


class CurrentOrderFragment : Fragment() {

    private val vm by inject<OrderViewModel>()
    private lateinit var orderAdapter: OrderAdapter
    private var _binding: FragmentCurrentOrderBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireParentFragment() as MainAppFragment).hideNavigation()
        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.btnAddToCart.setOnClickListener {
            val deliveryFragment = DeliveryFragment()
            val bundle = Bundle().apply {
                putString("amountOfOrder", binding.tvAmountOfOrder.text.toString())
                putString("deliveryPrice",binding.tvDeliveryPrice.text.toString())
                putString("totalPrice",binding.tvTotal.text.toString())
            }
            deliveryFragment.arguments = bundle

            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcvMainApp, deliveryFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvMyOrder.layoutManager = LinearLayoutManager(context)

        orderAdapter = OrderAdapter({ oldMenuItem, newMenuItem ->
            vm.updateItem(oldMenuItem,newMenuItem)
            vm.loadOrder()
        },{ menuItem ->
            vm.removeItem(menuItem)
            vm.loadOrder()
        })
        binding.rvMyOrder.adapter = orderAdapter
        val priceForDelivery = 3f
        vm.loadOrder()
        vm.orderLd.observe(viewLifecycleOwner) {

            when (it){
                is OrderScreenState.Success -> {
                    val orderList = it.list.toMutableList()
                    orderAdapter.orderList = orderList
                    orderAdapter.notifyDataSetChanged()

                    var totalSumWithoutDelivery = 0f
                    orderList.forEach { item ->
                        totalSumWithoutDelivery += item.price * item.quantity
                    }
                    val totalSum = totalSumWithoutDelivery + priceForDelivery

                    binding.tvAmountOfOrder.text = "$${"%.2f".format(totalSumWithoutDelivery)}"
                    binding.tvDeliveryPrice.text = "$${"%.2f".format(priceForDelivery)}"
                    binding.tvTotal.text = "$${"%.2f".format(totalSum)}"
                    binding.pbLoading.visibility = View.GONE
                }
                is OrderScreenState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}