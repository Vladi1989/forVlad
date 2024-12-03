package com.spase_y.vladfooddelivery.main.account

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.core.applyTheme
import com.spase_y.vladfooddelivery.databinding.FragmentAccountBinding
import com.spase_y.vladfooddelivery.main.root.MainAppFragment
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor
import org.koin.android.ext.android.inject


class AccountFragment : Fragment() {
    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private val themeInteractor:ThemeInteractor by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MainAppFragment.getInstance().showNavigation()
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

        val currentTheme = themeInteractor.getCurrentTheme()
        if (currentTheme == ThemeInteractor.ApplicationTheme.DAY) {
            binding.ivSwitchOn.visibility = View.VISIBLE
            binding.ivSwitchOff.visibility = View.GONE
        } else {
            binding.ivSwitchOn.visibility = View.GONE
            binding.ivSwitchOff.visibility = View.VISIBLE
        }

        binding.ivSwitchOn.setOnClickListener {
            binding.ivSwitchOff.visibility = View.VISIBLE
            binding.ivSwitchOn.visibility = View.GONE
            themeInteractor.changeCurrentTheme()
            requireActivity().applyTheme(themeInteractor)
        }

        binding.ivSwitchOff.setOnClickListener {
            binding.ivSwitchOff.visibility = View.GONE
            binding.ivSwitchOn.visibility = View.VISIBLE
            themeInteractor.changeCurrentTheme()
            requireActivity().applyTheme(themeInteractor)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}