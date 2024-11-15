package com.spase_y.vladfooddelivery.main.add_card.registration_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentAddCardRegistrationBinding


class AddCardRegistrationFragment : Fragment() {
    private var _binding: FragmentAddCardRegistrationBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCardRegistrationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}