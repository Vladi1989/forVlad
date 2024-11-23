package com.spase_y.vladfooddelivery.main.order.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentDeliveryBottomSheetBinding
import com.spase_y.vladfooddelivery.main.order.AddressNearYouFragment
import com.spase_y.vladfooddelivery.main.order.add_address.AddAddressFragment


class DeliveryBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy{
        FragmentDeliveryBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnDelivery.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddAddressFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.btnSelfDelivery.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction()
                .replace(R.id.main, AddressNearYouFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}