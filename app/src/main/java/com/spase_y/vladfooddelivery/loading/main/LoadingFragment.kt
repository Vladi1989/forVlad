package com.spase_y.vladfooddelivery.loading.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.databinding.FragmentLoadingBinding


class LoadingFragment : Fragment() {
    private val binding by lazy {
        FragmentLoadingBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFirstPageLetsGo.setOnClickListener {
            val bottomSheet = MainBottomSheetFragment()
            bottomSheet.show(parentFragmentManager,bottomSheet.tag)
        }
    }
}