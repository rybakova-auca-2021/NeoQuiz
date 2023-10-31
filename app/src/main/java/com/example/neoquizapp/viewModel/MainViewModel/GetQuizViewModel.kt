package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.mainModel.QuizDetail
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetQuizViewModel : ViewModel() {
    fun getQuiz(
        id: Int,
        onSuccess: (QuizDetail) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.mainApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.getQuiz(authHeader, id)
        call.enqueue(object : Callback<QuizDetail> {
            override fun onResponse(
                call: Call<QuizDetail>,
                response: Response<QuizDetail>
            ) {
                if (response.isSuccessful) {
                    val quizBody = response.body()
                    if (quizBody != null) {
                        onSuccess.invoke(quizBody)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<QuizDetail>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}