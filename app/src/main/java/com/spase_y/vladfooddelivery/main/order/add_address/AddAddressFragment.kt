package com.spase_y.vladfooddelivery.main.order.add_address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentAddAddressBinding
import com.spase_y.vladfooddelivery.main.add_card.registration_card.AddCardRegistrationFragment
import com.spase_y.vladfooddelivery.main.order.delivery.DeliveryFragment
import com.spase_y.vladfooddelivery.main.root.MainAppFragment


class AddAddressFragment : Fragment() {
    private var _binding: FragmentAddAddressBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddAddressBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainAppFragment.getInstance().hideNavigation()
        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnSave.setOnClickListener {
            requireActivity()
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, DeliveryFragment())
                .addToBackStack(null)
                .commit()
        }
        setFocusChangeListener(binding.etAddCountry)
        setFocusChangeListener(binding.etAddCity)
        setFocusChangeListener(binding.etAddShippingAddres)
        setFocusChangeListener(binding.etAddressNumber)
    }

    private fun setFocusChangeListener(editText: EditText) {
        editText.onFocusChangeListener = View.OnFocusChangeListener {_, hasFocus ->
            if(hasFocus){
                editText.setBackgroundResource(R.drawable.button_shape_stroke)
            } else {
                editText.setBackgroundResource(R.drawable.button_shape_stroke_gray)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}