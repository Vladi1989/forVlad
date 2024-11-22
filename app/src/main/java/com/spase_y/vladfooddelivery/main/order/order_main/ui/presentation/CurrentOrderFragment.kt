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
import com.spase_y.vladfooddelivery.main.order.delivery.DeliveryFragment
import com.spase_y.vladfooddelivery.main.order.order_main.ui.adapters.OrderAdapter
import com.spase_y.vladfooddelivery.main.order.order_main.ui.model.OrderScreenState
import com.spase_y.vladfooddelivery.main.order.order_main.ui.view_model.OrderViewModel
import org.koin.android.ext.android.inject


class CurrentOrderFragment : Fragment() {

    private val vm by inject<OrderViewModel>()
    private lateinit var orderAdapter: OrderAdapter
    private var _binding: FragmentCurrentOrderBinding? = null
    private val binding get() = _binding!!
    lateinit var theAmountOfTheOrder:TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
        vm.loadOrder()
        vm.orderLd.observe(viewLifecycleOwner) {

            when (it){
                is OrderScreenState.Success -> {
                    val orderList = it.list.toMutableList()
                    orderAdapter = OrderAdapter(orderList){ totalPrice ->
                        val deliveryPrice = 3f
                        val totalWithDelivery = totalPrice + deliveryPrice

                        binding.tvAmountOfOrder.text = "$${"%.2f".format(totalPrice)}"
                        binding.tvDeliveryPrice.text = "$${"%.2f".format(deliveryPrice)}"
                        binding.tvTotal.text = "$${"%.2f".format(totalWithDelivery)}"

                    }

                    var totalSumWithoutDelivery = 0f
                    var priceForDelivery = 3f


                    orderList.forEach { item ->
                        totalSumWithoutDelivery += item.price * item.quantity
                    }
                    val totalSum = totalSumWithoutDelivery + priceForDelivery

                    binding.tvAmountOfOrder.text = "$${"%.2f".format(totalSumWithoutDelivery)}"
                    binding.tvDeliveryPrice.text = "$${"%.2f".format(priceForDelivery)}"
                    binding.tvTotal.text = "$${"%.2f".format(totalSum)}"

                    binding.rvMyOrder.adapter = orderAdapter
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