package com.spase_y.vladfooddelivery.main.order.delivery

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentCardPaymentBottomSheetBinding
import com.spase_y.vladfooddelivery.main.add_card.registration_card.AddCardRegistrationFragment


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

        binding.btnCardPayment.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fcvMainApp,AddCardRegistrationFragment())
                .addToBackStack(null)
                .commit()
        }
    }


}