package com.spase_y.vladfooddelivery.main.menu.domain.model

import com.spase_y.vladfooddelivery.main.menu.data.model.Item

sealed class MenuResponse {
    data class Success(val items: List<Item>): MenuResponse()
    data class Error(val errorMessage: String): MenuResponse()
}