package com.example.neoquizapp.viewModel.AuthViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.RegisterData
import com.example.neoquizapp.model.RegisterResponse
import com.example.neoquizapp.model.ResponseMessage
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    fun register(
        username: String,
        email: String,
        password: String,
        passwordRepeat: String,
        onSuccess: () -> Unit,
        onError:() -> Unit
    ) {
        val request = RegisterData(username, email, password, passwordRepeat)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.registerUser(request)
        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                    val responseBody = response.body()
                    Utils.access = responseBody?.access
                    when (response.code()) {
                        200 -> {
                            val message = response.body()?.message ?: "Unknown response"
                            println(message)
                        }

                        400 -> {
                            val message = response.body()?.message ?: "Unknown response"
                            println(message)
                        }

                        406 -> {
                            val message = response.body()?.message ?: "Unknown response"
                            println(message)
                        }
                    }
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}