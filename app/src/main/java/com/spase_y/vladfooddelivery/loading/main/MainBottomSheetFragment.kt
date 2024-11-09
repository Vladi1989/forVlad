package com.spase_y.vladfooddelivery.loading.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentMainBottomSheetBinding
import com.spase_y.vladfooddelivery.loading.login.ui.presentation.PhoneNumberFragment
import com.spase_y.vladfooddelivery.loading.registration.ui.presentation.SetUpProfileFragment
import com.spase_y.vladfooddelivery.main.root.MainAppFragment


class MainBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy {
        FragmentMainBottomSheetBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSecondPageLetsGo.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction()
                .replace(R.id.main, PhoneNumberFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.btnCreateAccount.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction()
                .replace(R.id.main, SetUpProfileFragment())
                .addToBackStack(null)
                .commit()
        }
        binding.textViewMenu.setOnClickListener {
            dismiss()

            parentFragmentManager.beginTransaction().replace(R.id.main, MainAppFragment())
                .addToBackStack(null)
                .commit()
        }
    }
}