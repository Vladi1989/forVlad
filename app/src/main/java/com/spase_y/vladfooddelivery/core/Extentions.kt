package com.spase_y.vladfooddelivery.core

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import com.spase_y.vladfooddelivery.root.theme.domain.api.ThemeInteractor

fun Int.toPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
}
fun Activity.applyTheme(themeInteractor: ThemeInteractor) {
    val currentTheme = themeInteractor.getCurrentTheme()
    this.window.decorView.setBackgroundColor(
        if (currentTheme == ThemeInteractor.ApplicationTheme.DAY) Color.WHITE
        else Color.BLACK
    )
}

