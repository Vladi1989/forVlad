package com.spase_y.vladfooddelivery

data class Item(
    val item_calories: Int,
    val item_description: String,
    val item_id: String,
    val item_image: Int,
    val item_is_vegan: Boolean,
    val item_name: String,
    val item_price: Double
)