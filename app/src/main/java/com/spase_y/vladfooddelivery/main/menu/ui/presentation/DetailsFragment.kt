package com.spase_y.vladfooddelivery.main.menu.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentDetailsBinding
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import com.spase_y.vladfooddelivery.main.root.NavigationVisibilityController


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowBack7.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val controller = parentFragment as? NavigationVisibilityController
        controller?.hideNavigation()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}