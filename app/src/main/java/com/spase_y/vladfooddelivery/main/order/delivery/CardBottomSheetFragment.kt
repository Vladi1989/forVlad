package com.spase_y.vladfooddelivery.main.order.delivery

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spase_y.vladfooddelivery.databinding.FragmentCardPaymentBottomSheetBinding


class CardBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentCardPaymentBottomSheetBinding.inflate(layoutInflater)
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