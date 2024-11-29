package com.spase_y.vladfooddelivery.main.add_card.add_card

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentAddCardBinding
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.adapter.CardAdapter
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.model.CardScreenState
import com.spase_y.vladfooddelivery.main.add_card.add_card.ui.view_model.CardViewModel
import com.spase_y.vladfooddelivery.main.add_card.registration_card.AddCardRegistrationFragment
import org.koin.android.ext.android.inject


class AddCardFragment : Fragment() {
    private val vm by inject<CardViewModel>()
    private var _binding: FragmentAddCardBinding? = null
    private val binding get() = _binding!!
    private lateinit var cardAdapter: CardAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCardBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivArrowBack3.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.btnAddToCart.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fcvMainApp,AddCardRegistrationFragment())
                .addToBackStack(null)
                .commit()

        }
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        cardAdapter = CardAdapter()
        binding.recyclerView.adapter = cardAdapter

        vm.cardsLd.observe(viewLifecycleOwner) { state ->
            when (state) {
                is CardScreenState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                is CardScreenState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    cardAdapter.submitList(state.list)
                }
                is CardScreenState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}