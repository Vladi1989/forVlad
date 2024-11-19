package com.spase_y.vladfooddelivery.root.theme.domain.impl

import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeRepository

class ThemeInteractorImpl(
    private val repository: ThemeRepository
): ThemeInteractor {
    override fun getCurrentTheme(): ThemeInteractor.ApplicationTheme {
        return repository.getCurrentTheme()
    }

    override fun changeCurrentTheme() {
        repository.changeCurrentTheme()
    }
}