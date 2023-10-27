package com.example.neoquizapp.viewModel.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.ChangePassword
import com.example.neoquizapp.model.ResponseMessage
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {
    fun changePassword(
        password: String,
        password2: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        val request = ChangePassword(password, password2)
        val apiInterface = RetrofitInstance.authApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.changePassword(authHeader, request)
        call.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}