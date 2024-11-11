package com.spase_y.vladfooddelivery.root

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.spase_y.vladfooddelivery.loading.main.LoadingFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.loading.MainViewModel
import com.spase_y.vladfooddelivery.main.menu.ui.presentation.MenuFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, LoadingFragment())
                .commit()
        }

        if (viewModel.isUserLoggedIn()) {
            showMenu()
        } else {
            showMainBottomSheetFragment()
        }
    }

    private fun showMenu() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main, MenuFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun showMainBottomSheetFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main, LoadingFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }
}