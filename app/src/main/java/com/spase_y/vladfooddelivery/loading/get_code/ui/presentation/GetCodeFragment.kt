package com.spase_y.vladfooddelivery.loading.get_code.ui.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentGetCodeBinding
import com.spase_y.vladfooddelivery.loading.get_code.ui.model.GetCodeScreenState
import com.spase_y.vladfooddelivery.loading.get_code.ui.view_model.GetCodeViewModel
import com.spase_y.vladfooddelivery.loading.login.ui.presentation.PhoneNumberFragment.Companion.GET_CODE_TAG
import com.spase_y.vladfooddelivery.loading.result.FinalRegistrationFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class GetCodeFragment : Fragment() {
    private val binding by lazy {
        FragmentGetCodeBinding.inflate(layoutInflater)
    }
    private val vm: GetCodeViewModel by viewModel()
    private lateinit var validCode:String
    private lateinit var phoneNumber:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val validCode = requireArguments().getString(GET_CODE_TAG)
        val phoneNumber = requireArguments().getString("PHONE_NUMBER_TAG")?:""

        if(validCode.isNullOrEmpty()){
            Toast.makeText(requireContext(), "Что-то пошло не так", Toast.LENGTH_SHORT).show()
            return
        } 
        binding.ivArrowBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        setupEditTexts()
        setupFocusChangeListeners()
        setupObserver()

        binding.btnSendCode.setOnClickListener {
            val currentCode = binding.etCodeNumber1.text.toString() +
                    binding.etCodeNumber2.text.toString() +
                    binding.etCodeNumber3.text.toString() +
                    binding.etCodeNumber4.text.toString()

            if(currentCode == validCode){
                loginUser()
            } else {
                Toast.makeText(requireContext(), "Код введен не верно", Toast.LENGTH_SHORT).show()
            }
        }

        setupCodeVerification(validCode, phoneNumber)
    }
    private fun setupEditTexts(){
        binding.etCodeNumber1.addTextChangedListener(createTextWatcher(binding.etCodeNumber1, binding.etCodeNumber2, validCode, phoneNumber))
        binding.etCodeNumber2.addTextChangedListener(createTextWatcher(binding.etCodeNumber2, binding.etCodeNumber3, validCode, phoneNumber))
        binding.etCodeNumber3.addTextChangedListener(createTextWatcher(binding.etCodeNumber3, binding.etCodeNumber4, validCode, phoneNumber))
        binding.etCodeNumber4.addTextChangedListener(createTextWatcher(binding.etCodeNumber4, null, validCode, phoneNumber))
        binding.etCodeNumber2.setOnKeyListener(createBackKeyListener(binding.etCodeNumber1))
        binding.etCodeNumber3.setOnKeyListener(createBackKeyListener(binding.etCodeNumber2))
        binding.etCodeNumber4.setOnKeyListener(createBackKeyListener(binding.etCodeNumber3))
    }
    private fun setupFocusChangeListeners() {
        val focusedBackground = R.drawable.button_shape_stroke
        val defaultBackground = R.drawable.button_shape_stroke_gray

        fun updateBackground(editText: EditText, hasFocus: Boolean) {
            if (hasFocus || editText.text.isNotEmpty()) {
                editText.setBackgroundResource(focusedBackground)
            } else {
                editText.setBackgroundResource(defaultBackground)
            }
        }
        binding.etCodeNumber1.setOnFocusChangeListener { _, hasFocus ->
            updateBackground(binding.etCodeNumber1, hasFocus)
        }
        binding.etCodeNumber2.setOnFocusChangeListener { _, hasFocus ->
            updateBackground(binding.etCodeNumber2, hasFocus)
        }
        binding.etCodeNumber3.setOnFocusChangeListener { _, hasFocus ->
            updateBackground(binding.etCodeNumber3, hasFocus)
        }
        binding.etCodeNumber4.setOnFocusChangeListener { _, hasFocus ->
            updateBackground(binding.etCodeNumber4, hasFocus)
        }
    }
    private fun createTextWatcher(currentEditText: EditText, nextEditText: EditText?, validCode: String,phoneNumber: String): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if(!s.isNullOrEmpty()){
                    nextEditText?.requestFocus()
                }
                val code1 = binding.etCodeNumber1.text.toString()
                val code2 = binding.etCodeNumber2.text.toString()
                val code3 = binding.etCodeNumber3.text.toString()
                val code4 = binding.etCodeNumber4.text.toString()

                vm.verifyCode(code1 + code2 + code3 + code4, validCode, phoneNumber)
            }
        }
    }
    private fun createBackKeyListener(prevEditText: EditText):View.OnKeyListener{
        return View.OnKeyListener {v, keyCode, event ->
            if(event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL &&  (v as EditText).text.isEmpty()) {
                prevEditText.requestFocus()
            }
            false
        }
    }
    private fun setupObserver() {
        vm.screenStateLd.observe(viewLifecycleOwner) {
            when (it) {
                is GetCodeScreenState.CanGoNext -> {
                    binding.btnSendCode.alpha = 1f
                    binding.btnSendCode.isEnabled = true
                }
                is GetCodeScreenState.CantGoNext -> {
                    binding.btnSendCode.alpha = 0.7f
                    binding.btnSendCode.isEnabled = false
                }
            }
        }
    }

    private fun setupCodeVerification(validCode: String, phoneNumber: String) {
        val currentCode = binding.etCodeNumber1.text.toString() +
                binding.etCodeNumber2.text.toString() +
                binding.etCodeNumber3.text.toString() +
                binding.etCodeNumber4.text.toString()

        vm.verifyCode(currentCode, validCode, phoneNumber)
    }


    fun loginUser(){
        val finalRegistrationFragment = FinalRegistrationFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.main,finalRegistrationFragment)
            .addToBackStack(null)
            .commit()
    }
}