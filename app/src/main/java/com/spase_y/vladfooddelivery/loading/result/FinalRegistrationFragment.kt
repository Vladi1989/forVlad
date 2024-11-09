package com.spase_y.vladfooddelivery.loading.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentFinalRegistrationBinding
import com.spase_y.vladfooddelivery.main.root.MainAppFragment


class FinalRegistrationFragment : Fragment() {
    private val binding by lazy{
        FragmentFinalRegistrationBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnFirstPageLetsGo.setOnClickListener {
            openApp()
        }
    }
    fun openApp(){
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, MainAppFragment())
            .commit()
    }
}
