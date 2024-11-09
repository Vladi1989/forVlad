package com.spase_y.vladfooddelivery.loading.login.ui.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.spase_y.vladfooddelivery.loading.login.data.model.CodeModel
import com.spase_y.vladfooddelivery.loading.login.ui.model.PhoneNumberScreenState
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

class PhoneNumberViewModel {

    val screenStateLD = MutableLiveData<PhoneNumberScreenState>()

    fun isValidNumber(number: String){
        val isValidNumber = number.length in 7..14
        if (isValidNumber) screenStateLD.postValue(PhoneNumberScreenState.CanGoNext)
        else screenStateLD.postValue(PhoneNumberScreenState.CantGoNext)
    }

    private val okHttpClient = OkHttpClient()
    fun getCode(number: String) {
        screenStateLD.postValue(PhoneNumberScreenState.Loading)
        screenStateLD.postValue(PhoneNumberScreenState.Result("123"))

        val request = Request.Builder()
            .url("https://raw.githubusercontent.com/Vlad21anon/bgResponse/main/get_code3")
            .build()
        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                screenStateLD.postValue(PhoneNumberScreenState.Error(e.message.toString()))
            }

            override fun onResponse(call: Call, response: Response) {
                if(response.isSuccessful){
                    val serverResponse = response.body?.string()
                    Log.d("TAG",serverResponse.toString())
                    if (serverResponse.isNullOrEmpty()){
                        screenStateLD.postValue(PhoneNumberScreenState.Error("Error"))
                        return
                    }
                    val model = Gson().fromJson(serverResponse,CodeModel::class.java)
                    if (model.result.isNullOrEmpty()){
                        screenStateLD.postValue(PhoneNumberScreenState.Error("Error"))
                        return
                    }
                    if (model.result != "null"){
                        screenStateLD.postValue(PhoneNumberScreenState.Result(model.result))
                    } else {
                        screenStateLD.postValue(PhoneNumberScreenState.Error("Такого пользователя еще не было зарегистрировано"))
                    }

                }
                else {
                    screenStateLD.postValue(PhoneNumberScreenState.Error("Error"))
                }
            }

        })
    }
}