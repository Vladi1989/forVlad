package com.spase_y.vladfooddelivery.loading.login.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentPhoneNumberBinding
import com.spase_y.vladfooddelivery.loading.get_code.ui.presentation.GetCodeFragment
import com.spase_y.vladfooddelivery.loading.login.ui.model.PhoneNumberScreenState
import com.spase_y.vladfooddelivery.loading.login.ui.view_model.PhoneNumberViewModel


class PhoneNumberFragment : Fragment() {
    private val binding by lazy {
        FragmentPhoneNumberBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val vm = PhoneNumberViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowBack1.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.etPhoneNumber.doOnTextChanged { text, start, before, count ->
            if (!text.toString().contains("+")){
                binding.etPhoneNumber.setText("+$text")
                binding.etPhoneNumber.setSelection(binding.etPhoneNumber.text.length)
            }
            vm.isValidNumber(binding.etPhoneNumber.text.toString())
        }

        binding.btnSendCode.setOnClickListener {
            vm.getCode(binding.etPhoneNumber.text.toString())

        }
        setupObservers()
    }

    private fun setupObservers() {
        vm.screenStateLD.observe(viewLifecycleOwner){
            when(it){
                is PhoneNumberScreenState.CanGoNext -> {
                    binding.btnSendCode.alpha = 1f
                    binding.btnSendCode.isEnabled = true
                }
                is PhoneNumberScreenState.CantGoNext -> {
                    binding.btnSendCode.alpha = 0.7f
                    binding.btnSendCode.isEnabled = false
                }
                is PhoneNumberScreenState.Loading -> {
                    binding.btnSendCode.alpha = 0.7f
                    binding.btnSendCode.isEnabled = false
                    binding.pbLoading.visibility = View.VISIBLE
                }
                is PhoneNumberScreenState.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    binding.pbLoading.visibility = View.GONE
                    binding.btnSendCode.alpha = 1f
                    binding.btnSendCode.isEnabled = true

                }
                is PhoneNumberScreenState.Result -> {
                    binding.pbLoading.visibility = View.GONE
                    openEnterCodeFragment(it.result)
                }
            }
        }
    }
    
    fun openEnterCodeFragment(result: String) {
        val getCodeFragment = GetCodeFragment()
        val newArguments = Bundle()
        newArguments.putString(GET_CODE_TAG,result)
        getCodeFragment.arguments = newArguments
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, getCodeFragment)
            .addToBackStack(null)
            .commit()
    }
    companion object{
        const val GET_CODE_TAG = "GET_CODE_TAG"
    }
}