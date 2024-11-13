package com.spase_y.vladfooddelivery.loading.registration.ui.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.widget.doOnTextChanged
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentSetUpProfileBinding
import com.spase_y.vladfooddelivery.loading.get_code.ui.presentation.GetCodeFragment
import com.spase_y.vladfooddelivery.loading.registration.ui.model.SetupProfileScreenState
import com.spase_y.vladfooddelivery.loading.registration.ui.view_model.SetupProfileViewModel
import com.spase_y.vladfooddelivery.root.MainActivity


class SetUpProfileFragment : Fragment() {

    private val binding by lazy {
        FragmentSetUpProfileBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    val vm = SetupProfileViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etFirstName.onFocusChangeListener = object : View.OnFocusChangeListener{
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if(hasFocus){
                    binding.etFirstName.setBackgroundResource(R.drawable.button_shape_stroke)
                } else binding.etFirstName.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }

        binding.etDateOfBirth.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    binding.etDateOfBirth.setBackgroundResource(R.drawable.button_shape_stroke)
                } else binding.etDateOfBirth.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }
        binding.etPhoneNumberProfile.onFocusChangeListener = object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                if (hasFocus) {
                    binding.etPhoneNumberProfile.setBackgroundResource(R.drawable.button_shape_stroke)
                } else binding.etPhoneNumberProfile.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }
        binding.etFirstName.doOnTextChanged { text, start, before, count ->
            vm.isValidName(binding.etFirstName.text.toString())
        }
        binding.etPhoneNumberProfile.doOnTextChanged { text, start, before, count ->
            if(!text.toString().contains("+")){
                binding.etPhoneNumberProfile.setText("+$text")
                binding.etPhoneNumberProfile.setSelection(binding.etPhoneNumberProfile.text.length)
            }
            vm.isValidNumber(binding.etPhoneNumberProfile.text.toString())
        }
        setupDateEditText()
        binding.cbAgree.setOnCheckedChangeListener(object :CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                vm.isAcceptPrivacy(isChecked)
            }
        })
        binding.ivArrowBack2.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnActiveSendMeTheCodeProfile.setOnClickListener {
            openEnterCodeFragment()
        }
        setupObservers()
    }
    private fun setupObservers() {
        vm.screenStateLD.observe(viewLifecycleOwner){
            when(it){
                is SetupProfileScreenState.CanGoNext -> {
                    binding.btnActiveSendMeTheCodeProfile.alpha = 1f
                    binding.btnActiveSendMeTheCodeProfile.isEnabled = true
                }
                is SetupProfileScreenState.CantGoNext -> {
                    binding.btnActiveSendMeTheCodeProfile.alpha = 0.7f
                    binding.btnActiveSendMeTheCodeProfile.isEnabled = false
                }
                is SetupProfileScreenState.Loading -> {

                }
            }
        }
    }

    fun openEnterCodeFragment(){
        val number = binding.etPhoneNumberProfile.text.toString()
        (requireActivity() as MainActivity).viewModel.setNumber(number)
        parentFragmentManager.beginTransaction()
            .replace(R.id.main, GetCodeFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setupDateEditText() {
        binding.etDateOfBirth.addTextChangedListener(object : TextWatcher {
            var isUpdating = false
            val pattern = "##.##.####"

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (isUpdating) return
                isUpdating = true

                val input = s.toString().replace(".", "")
                val formatted = StringBuilder()
                var count = 0

                for (char in input) {
                    if (count == 2 || count == 4) {
                        formatted.append(".")
                    }
                    formatted.append(char)
                    count++
                }
                binding.etDateOfBirth.setText(formatted.toString())
                binding.etDateOfBirth.setSelection(formatted.length)

                if (formatted.length == 10) {
                    binding.etDateOfBirth.setBackgroundResource(R.drawable.button_shape_stroke)
                } else {
                    binding.etDateOfBirth.setBackgroundResource(R.drawable.button_shape_stroke_gray)
                }
                isUpdating = false
                vm.isValidDate(formatted.toString())
            }
        })
    }
}