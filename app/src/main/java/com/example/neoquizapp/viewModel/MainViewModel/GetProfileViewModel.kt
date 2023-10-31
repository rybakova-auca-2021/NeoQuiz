package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.ProfileData
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetProfileViewModel : ViewModel() {
    fun getProfile(
        onSuccess: (ProfileData) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.authApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.getProfile(authHeader)
        call.enqueue(object : Callback<ProfileData> {
            override fun onResponse(
                call: Call<ProfileData>,
                response: Response<ProfileData>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    if (profile != null) {
                        onSuccess.invoke(profile)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProfileData>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}