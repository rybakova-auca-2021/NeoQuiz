package com.example.neoquizapp.viewModel.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.PasswordResetRequest
import com.example.neoquizapp.model.ResponseMessage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SendCodeViewModel : ViewModel() {
    fun sendCode(
        username: String,
        mail: String,
        onSuccess: () -> Unit,
    ) {
        val request = PasswordResetRequest(mail, username)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.requestPasswordReset(request)
        call.enqueue(object : Callback<ResponseMessage> {
            override fun onResponse(
                call: Call<ResponseMessage>,
                response: Response<ResponseMessage>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ResponseMessage>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}