package com.spase_y.vladfooddelivery.main.menu.ui.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentDetailsBinding
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.root.MainAppFragment


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
        MainAppFragment.getInstance().hideNavigation()

        val jsonItem = arguments?.getString("item")
        val item = jsonItem.let { Gson().fromJson(it, Item::class.java) }

        item?.let {
            binding.tvDetailsNameFood.text = it.item_name
            binding.tvDetailsDescriptions.text = it.item_description
            binding.tvKcal.text = it.item_calories.toString()
            binding.tvPriceDetails.text = it.item_price.toString()

            Glide.with(this)
                .load(it.item_image)
                .into(binding.ivDetailsImage)

        }
        binding.ivArrowBack7.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}