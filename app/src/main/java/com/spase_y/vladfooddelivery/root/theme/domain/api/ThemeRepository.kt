package com.spase_y.vladfooddelivery.root.theme.domain.api

interface ThemeRepository {
    fun getCurrentTheme(): ThemeInteractor.ApplicationTheme
    fun changeCurrentTheme()
}