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
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.main.account.AccountFragment
import com.spase_y.vladfooddelivery.main.discounts.DiscountsFragment
import com.spase_y.vladfooddelivery.main.menu.ui.presentation.MenuFragment
import com.spase_y.vladfooddelivery.main.order.list_orders.ListOrdersFragment
import com.spase_y.vladfooddelivery.main.order.order_main.ui.presentation.CurrentOrderFragment


class MainAppFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_app, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        replaceFragment(MenuFragment())

        setActiveNavIcon(R.id.ll_nav_home)

        val cart = view.findViewById<CardView>(R.id.cvCart)
        val navHome = view.findViewById<LinearLayout>(R.id.ll_nav_home)
        val navDiscounts = view.findViewById<LinearLayout>(R.id.ll_nav_discounts)
        val navOrder = view.findViewById<LinearLayout>(R.id.ll_nav_order)
        val navAccount = view.findViewById<LinearLayout>(R.id.ll_nav_account)


        navHome.setOnClickListener{
            replaceFragment(MenuFragment())
            setActiveNavIcon(R.id.ll_nav_home)
        }
        navDiscounts.setOnClickListener{
            replaceFragment(DiscountsFragment())
            setActiveNavIcon(R.id.ll_nav_discounts)
        }
        navOrder.setOnClickListener {
            replaceFragment(ListOrdersFragment())
            setActiveNavIcon(R.id.ll_nav_order)
        }
        navAccount.setOnClickListener {
            replaceFragment(AccountFragment())
            setActiveNavIcon(R.id.ll_nav_account)
        }

    }


    private fun replaceFragment(fragment: Fragment){
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.fcvMainApp,fragment)
            .addToBackStack(null)
            .commit()
    }



    private fun setActiveNavIcon(activeNavId: Int) {
        view?.findViewById<ImageView>(R.id.iv_icon_home)?.apply {
            setImageResource(R.drawable.buttom_nav_btn_1_negativ)
            layoutParams = layoutParams.apply {
                width = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
                height = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
            }
            view?.findViewById<TextView>(R.id.tv_text_home)?.setTextColor(resources.getColor(R.color.text4))
        }
        view?.findViewById<ImageView>(R.id.iv_icon_discounts)?.apply {
            setImageResource(R.drawable.buttom_nav_btn_2_negativ)
            layoutParams = layoutParams.apply {
                width = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
                height = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
            }
            view?.findViewById<TextView>(R.id.tv_text_discounts)?.setTextColor(resources.getColor(R.color.text4))
        }
        view?.findViewById<ImageView>(R.id.iv_icon_order)?.apply {
            setImageResource(R.drawable.buttom_nav_btn_3_negativ)
            layoutParams = layoutParams.apply {
                width = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
                height = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
            }
            view?.findViewById<TextView>(R.id.tv_text_order)?.setTextColor(resources.getColor(R.color.text4))
        }
        view?.findViewById<ImageView>(R.id.iv_icon_account)?.apply {
            setImageResource(R.drawable.buttom_nav_btn_4_negativ)
            layoutParams = layoutParams.apply {
                width = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
                height = resources.getDimensionPixelSize(R.dimen.inactive_icon_size) // 24dp
            }
            view?.findViewById<TextView>(R.id.tv_text_account)?.setTextColor(resources.getColor(R.color.text4))
        }

        when (activeNavId) {
            R.id.ll_nav_home -> view?.findViewById<ImageView>(R.id.iv_icon_home)?.apply {
                setImageResource(R.drawable.activ_button_1)
                layoutParams = layoutParams.apply {
                    width = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                    height = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                }
                view?.findViewById<TextView>(R.id.tv_text_home)?.setTextColor(resources.getColor(R.color.primary))
            }
            R.id.ll_nav_discounts -> view?.findViewById<ImageView>(R.id.iv_icon_discounts)?.apply {
                setImageResource(R.drawable.activ_button_2)
                layoutParams = layoutParams.apply {
                    width = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                    height = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                }
                view?.findViewById<TextView>(R.id.tv_text_discounts)?.setTextColor(resources.getColor(R.color.primary))
            }
            R.id.ll_nav_order -> view?.findViewById<ImageView>(R.id.iv_icon_order)?.apply {
                setImageResource(R.drawable.activ_button_3)
                layoutParams = layoutParams.apply {
                    width = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                    height = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                }
                view?.findViewById<TextView>(R.id.tv_text_order)?.setTextColor(resources.getColor(R.color.primary))
            }
            R.id.ll_nav_account -> view?.findViewById<ImageView>(R.id.iv_icon_account)?.apply {
                setImageResource(R.drawable.activ_button_4)
                layoutParams = layoutParams.apply {
                    width = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                    height = resources.getDimensionPixelSize(R.dimen.active_icon_size) // 36dp
                }
                view?.findViewById<TextView>(R.id.tv_text_account)?.setTextColor(resources.getColor(R.color.primary))
            }
        }
    }
}