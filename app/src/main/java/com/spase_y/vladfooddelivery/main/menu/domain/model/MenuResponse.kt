package com.spase_y.vladfooddelivery.main.menu.domain.model

import com.spase_y.vladfooddelivery.main.menu.data.model.ListMenu

interface MenuResponse {
    class Success(val items: ListMenu): MenuResponse
    class Error(val errorMessage: String): MenuResponse
}