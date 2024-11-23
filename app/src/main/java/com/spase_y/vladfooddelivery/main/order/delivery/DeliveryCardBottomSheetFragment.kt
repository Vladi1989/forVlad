package com.spase_y.vladfooddelivery.main.order.delivery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentDeliveryCardPaymentBottomSheetBinding


class DeliveryCardBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy{
        FragmentDeliveryCardPaymentBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}