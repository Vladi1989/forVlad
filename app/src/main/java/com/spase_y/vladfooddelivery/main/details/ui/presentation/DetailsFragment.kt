package com.spase_y.vladfooddelivery.main.details.ui.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.spase_y.vladfooddelivery.databinding.FragmentDetailsBinding
import com.spase_y.vladfooddelivery.main.details.ui.view_model.DetailsViewModel
import com.spase_y.vladfooddelivery.main.menu.data.model.Item
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainAppFragment.getInstance().hideNavigation()

        val item = arguments?.getString("item")?.let {
            try {
                Gson().fromJson(it, Item::class.java)
            } catch (e: JsonSyntaxException) {
                null
            }
        }

        if (item == null) {
            showToast("Не удалось загрузить данные.")
            return
        }

        displayItemDetails(item)

        binding.ivArrowBack7.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun displayItemDetails(item: Item) {
        binding.tvDetailsNameFood.text = item.itemName
        binding.tvDetailsDescriptions.text = item.itemDescription
        binding.tvKcal.text = item.itemCalories.toString()
        binding.tvPriceDetails.text = item.itemPrice.toString()

        Glide.with(this)
            .load(item.itemImage)
            .into(binding.ivDetailsImage)

        detailsViewModel.saveItemToRecommend(item.itemId)
        showToast("Добавлено в рекомендованные: ${item.itemName}")
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}