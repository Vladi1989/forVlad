package com.spase_y.vladfooddelivery.root.theme.domain.api

interface ThemeInteractor {
    fun getCurrentTheme(): ApplicationTheme
    fun changeCurrentTheme()

    enum class ApplicationTheme(){
        DAY,
        NIGHT
    }
}