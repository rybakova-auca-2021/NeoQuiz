package com.example.neoquizapp.viewModel.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.ResponseMessage
import com.example.neoquizapp.model.TokenData
import com.example.neoquizapp.model.VerifyCodeData
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyCodeViewModel : ViewModel() {
    fun verifyCode(
        username: String,
        code: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val request = VerifyCodeData(code, username)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.verifyCode(request)
        call.enqueue(object : Callback<TokenData> {
            override fun onResponse(
                call: Call<TokenData>,
                response: Response<TokenData>
            ) {
                if (response.isSuccessful) {
                    val response = response.body()
                    Utils.access = response?.access
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenData>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}