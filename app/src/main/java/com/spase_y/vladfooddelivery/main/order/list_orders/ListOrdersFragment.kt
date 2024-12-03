package com.spase_y.vladfooddelivery.main.order.list_orders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentListOrdersBinding
import com.spase_y.vladfooddelivery.main.root.MainAppFragment


class ListOrdersFragment : Fragment() {
    private var _binding: FragmentListOrdersBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListOrdersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainAppFragment.getInstance().showNavigation()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}