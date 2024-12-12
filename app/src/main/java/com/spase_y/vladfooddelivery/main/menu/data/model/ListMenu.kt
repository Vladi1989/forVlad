package com.spase_y.vladfooddelivery.main.menu.data.model

import com.google.gson.annotations.SerializedName

data class ListMenu(
    @SerializedName("pizza_items") val pizzaItems: List<Item>,
    @SerializedName("burgers_items") val burgersItems: List<Item>,
    @SerializedName("wok_items") val wokItems: List<Item>,
    @SerializedName("sushi_items") val sushiItems: List<Item>
)