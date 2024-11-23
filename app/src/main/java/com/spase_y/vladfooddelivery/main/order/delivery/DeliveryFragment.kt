package com.spase_y.vladfooddelivery.main.order.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentDeliveryBinding
import com.spase_y.vladfooddelivery.main.order.add_address.AddAddressFragment


class DeliveryFragment : Fragment() {
    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDeliveryBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.etAddComment.onFocusChangeListener = View.OnFocusChangeListener{_,hasFocus ->
            if(hasFocus){
                binding.etAddComment.setBackgroundResource(R.drawable.button_shape_stroke)
            } else {
                binding.etAddComment.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }

        arguments?.let { bundle ->
            val amountOfOrder = bundle.getString("amountOfOrder") ?: "$0.00"
            val deliveryPrice = bundle.getString("deliveryPrice") ?: "$0.00"
            val totalPrice  = bundle.getString("totalPrice") ?: "$0.00"

            binding.tvAmountOfOrder.text = amountOfOrder
            binding.tvDeliveryPrice.text = deliveryPrice
            binding.tvTotal.text = totalPrice

        }
        binding.btnGoPayment.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fcvMainApp,AddAddressFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}