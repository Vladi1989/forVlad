package com.spase_y.vladfooddelivery.root.theme.data

import android.content.SharedPreferences
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeRepository

class ThemeRepositoryImpl(private val sharedPreferences: SharedPreferences):
    ThemeRepository {
    override fun getCurrentTheme(): ThemeInteractor.ApplicationTheme {
        val themeName = sharedPreferences.getString(THEME_KEY, DAY_THEME)
        return if (themeName == NIGHT_THEME){
            ThemeInteractor.ApplicationTheme.NIGHT
        } else {
            ThemeInteractor.ApplicationTheme.DAY
        }
    }

    override fun changeCurrentTheme() {
        val currentTheme = getCurrentTheme()
        val newTheme = if(currentTheme == ThemeInteractor.ApplicationTheme.DAY){
            NIGHT_THEME
        } else {
            DAY_THEME
        }
        sharedPreferences.edit().putString(THEME_KEY,newTheme).apply()
    }
    companion object{
        private const val THEME_KEY = "THEME_KEY"
        private const val DAY_THEME = "DAY_THEME"
        private const val NIGHT_THEME = "NIGHT"
    }
}