package com.spase_y.vladfooddelivery.main.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cvHelpOff.setOnClickListener {
            binding.cvHelpOff.visibility = View.GONE
            binding.cvHelpOn.visibility = View.VISIBLE
        }
        binding.cvHelpOn.setOnClickListener {
            binding.cvHelpOff.visibility = View.VISIBLE
            binding.cvHelpOn.visibility = View.GONE
        }
        binding.cvLanguageOff.setOnClickListener {
            binding.cvLanguageOff.visibility = View.GONE
            binding.cvLanguageOn.visibility = View.VISIBLE
        }
        binding.cvLanguageOn.setOnClickListener {
            binding.cvLanguageOff.visibility = View.VISIBLE
            binding.cvLanguageOn.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}