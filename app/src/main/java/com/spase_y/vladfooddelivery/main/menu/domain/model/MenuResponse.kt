package com.spase_y.vladfooddelivery.main.menu.domain.model

import com.spase_y.vladfooddelivery.main.menu.data.model.Item

interface MenuResponse {
    class Success(val items: List<Item>): MenuResponse
    class Error(val errorMessage: String): MenuResponse
}