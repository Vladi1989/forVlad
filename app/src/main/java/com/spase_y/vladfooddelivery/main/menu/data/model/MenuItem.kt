package com.spase_y.vladfooddelivery.main.menu.data.model

data class MenuItem(
    val imageRes:Int,
    var name: String,
    val description: String,
    val price: Float,
    var quantity: Int = 1
)