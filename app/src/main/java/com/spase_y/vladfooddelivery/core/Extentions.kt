package com.spase_y.vladfooddelivery.core

import android.content.Context
import android.util.TypedValue

fun Int.toPx(context: Context): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
}
