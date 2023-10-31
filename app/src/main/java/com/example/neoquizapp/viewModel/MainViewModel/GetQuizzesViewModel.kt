package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.mainModel.Quiz
import com.example.neoquizapp.model.mainModel.QuizResult
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetQuizzesViewModel : ViewModel() {
    fun getQuizzes(
        onSuccess: (List<Quiz>) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.mainApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.getQuizzes(authHeader)
        call.enqueue(object : Callback<QuizResult> {
            override fun onResponse(
                call: Call<QuizResult>,
                response: Response<QuizResult>
            ) {
                if (response.isSuccessful) {
                    val quizBody = response.body()
                    if (quizBody != null) {
                        onSuccess.invoke(quizBody.list)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<QuizResult>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}