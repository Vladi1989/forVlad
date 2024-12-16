package com.spase_y.vladfooddelivery.main.menu.data.model

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("item_id") val itemId: String,
    @SerializedName("item_name") val itemName: String,
    @SerializedName("item_description") val itemDescription: String,
    @SerializedName("item_price") val itemPrice: Double,
    @SerializedName("item_image") val itemImage: String,
    @SerializedName("item_is_vegan") val itemIsVegan: Boolean,
    @SerializedName("item_calories") val itemCalories: Int,
    @SerializedName("quantity") val quantity: Int = -1
)