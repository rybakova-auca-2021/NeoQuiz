package com.example.neoquizapp.viewModel.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.LoginData
import com.example.neoquizapp.model.TokenData
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    fun login(
        login: String,
        password: String,
        onSuccess: () -> Unit,
        onError:() -> Unit
    ) {
        val request = LoginData(login, password)
        val apiInterface = RetrofitInstance.authApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.login(authHeader, request)
        call.enqueue(object : Callback<TokenData> {
            override fun onResponse(
                call: Call<TokenData>,
                response: Response<TokenData>
            ) {
                if (response.isSuccessful) {
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