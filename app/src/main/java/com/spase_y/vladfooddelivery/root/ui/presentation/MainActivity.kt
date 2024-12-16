package com.spase_y.vladfooddelivery.root.ui.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.spase_y.vladfooddelivery.loading.main.LoadingFragment
import com.spase_y.vladfooddelivery.R
import com.spase_y.vladfooddelivery.databinding.ActivityMainBinding
import com.spase_y.vladfooddelivery.root.ui.model.MainScreenState
import com.spase_y.vladfooddelivery.root.ui.view_model.MainViewModel
import com.spase_y.vladfooddelivery.main.menu.ui.presentation.MenuFragment
import com.spase_y.vladfooddelivery.root.login_status.domain.api.LoginUserInteractor
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.primary)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.main, LoadingFragment())
                .commit()
        }
        viewModel.getMainLd().observe(this) { state ->
            when (state) {
                is MainScreenState.Loading -> {
                    binding.pbMain.visibility = View.VISIBLE
                }
                is MainScreenState.Result -> {
                    binding.pbMain.visibility = View.INVISIBLE
                    if (state.isUserLoggedIn == LoginUserInteractor.UserStatus.LOGIN) {
                        showMenu()
                    } else {
                        showLoadingFragment()
                    }
                }
                else -> {}
            }
        }
    }
    private fun showLoadingFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, LoadingFragment())
            .commit()
    }

    private fun showMenu() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main, MenuFragment())
            .addToBackStack(null)
            .commit()
    }
}