package com.spase_y.vladfooddelivery.main.root

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.github.captain_miao.optroundcardview.OptRoundCardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.FragmentMainAppBinding
import com.spase_y.vladfooddelivery.main.account.AccountFragment
import com.spase_y.vladfooddelivery.main.discounts.DiscountsFragment
import com.spase_y.vladfooddelivery.main.menu.ui.presentation.MenuFragment
import com.spase_y.vladfooddelivery.main.order.list_orders.ListOrdersFragment


class MainAppFragment : Fragment() {

    private var _binding: FragmentMainAppBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainAppBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(MenuFragment())
        setActiveNavIcon(R.id.ll_nav_home)

        with(binding.customBottomNav) {
            llNavHome.setOnClickListener {
                animateNavItem(llNavHome)
                replaceFragment(MenuFragment())
                setActiveNavIcon(R.id.ll_nav_home)
            }

            llNavDiscounts.setOnClickListener {
                animateNavItem(llNavDiscounts)
                replaceFragment(DiscountsFragment())
                setActiveNavIcon(R.id.ll_nav_discounts)
            }

            llNavOrder.setOnClickListener {
                animateNavItem(llNavOrder)
                replaceFragment(ListOrdersFragment())
                setActiveNavIcon(R.id.ll_nav_order)
            }

            llNavAccount.setOnClickListener {
                animateNavItem(llNavAccount)
                replaceFragment(AccountFragment())
                setActiveNavIcon(R.id.ll_nav_account)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun hideNavigation() {
        val bottomNav = requireActivity().findViewById<View>(R.id.customBottomNav)
        bottomNav.visibility = View.GONE
    }

    fun showNavigation() {
        val bottomNav = requireActivity().findViewById<View>(R.id.customBottomNav)
        bottomNav.visibility = View.VISIBLE
    }


    private fun replaceFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMainApp, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setActiveNavIcon(activeNavId: Int) {
        with(binding) {
            resetNavIcons()

            when (activeNavId) {
                R.id.ll_nav_home -> {
                    binding.customBottomNav.ivIconHome.setImageResource(R.drawable.activ_button_1)
                    binding.customBottomNav.tvTextHome.setTextColor(resources.getColor(R.color.primary))
                    resizeIcon(binding.customBottomNav.ivIconHome, true)
                }
                R.id.ll_nav_discounts -> {
                    binding.customBottomNav.ivIconDiscounts.setImageResource(R.drawable.activ_button_2)
                    binding.customBottomNav.tvTextDiscounts.setTextColor(resources.getColor(R.color.primary))
                    resizeIcon(binding.customBottomNav.ivIconDiscounts, true)
                }
                R.id.ll_nav_order -> {
                    binding.customBottomNav.ivIconOrder.setImageResource(R.drawable.activ_button_3)
                    binding.customBottomNav.tvTextOrder.setTextColor(resources.getColor(R.color.primary))
                    resizeIcon(binding.customBottomNav.ivIconOrder, true)
                }
                R.id.ll_nav_account -> {
                    binding.customBottomNav.ivIconAccount.setImageResource(R.drawable.activ_button_4)
                    binding.customBottomNav.tvTextAccount.setTextColor(resources.getColor(R.color.primary))
                    resizeIcon(binding.customBottomNav.ivIconAccount, true)
                }
            }
        }
    }

    private fun resetNavIcons() {
        with(binding) {
            val inactiveColor = resources.getColor(R.color.text4)
            val inactiveSize = resources.getDimensionPixelSize(R.dimen.inactive_icon_size)

            binding.customBottomNav.ivIconHome.setImageResource(R.drawable.buttom_nav_btn_1_negativ)
            binding.customBottomNav.tvTextHome.setTextColor(inactiveColor)
            resizeIcon(binding.customBottomNav.ivIconHome, false)

            binding.customBottomNav.ivIconDiscounts.setImageResource(R.drawable.buttom_nav_btn_2_negativ)
            binding.customBottomNav.tvTextDiscounts.setTextColor(inactiveColor)
            resizeIcon(binding.customBottomNav.ivIconDiscounts, false)

            binding.customBottomNav.ivIconOrder.setImageResource(R.drawable.buttom_nav_btn_3_negativ)
            binding.customBottomNav.tvTextOrder.setTextColor(inactiveColor)
            resizeIcon(binding.customBottomNav.ivIconOrder, false)

            binding.customBottomNav.ivIconAccount.setImageResource(R.drawable.buttom_nav_btn_4_negativ)
            binding.customBottomNav.tvTextAccount.setTextColor(inactiveColor)
            resizeIcon(binding.customBottomNav.ivIconAccount, false)
        }
    }

    private fun resizeIcon(icon: ImageView, isActive: Boolean) {
        val size = if (isActive) {
            resources.getDimensionPixelSize(R.dimen.active_icon_size)
        } else {
            resources.getDimensionPixelSize(R.dimen.inactive_icon_size)
        }
        icon.layoutParams = icon.layoutParams.apply {
            width = size
            height = size
        }
    }

    private fun animateNavItem(view: View) {
        YoYo.with(Techniques.Bounce)
            .duration(700)
            .playOn(view)
    }
}




