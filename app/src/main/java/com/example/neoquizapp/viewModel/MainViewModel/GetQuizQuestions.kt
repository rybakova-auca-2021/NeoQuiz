package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.mainModel.Answers
import com.example.neoquizapp.model.mainModel.Question
import com.example.neoquizapp.model.mainModel.QuizSubmission
import com.example.neoquizapp.model.mainModel.QuizSubmissionResponse
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetQuizQuestions : ViewModel() {
    fun getQuizQuestions(
        id: Int,
        onSuccess: (List<Question>) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.mainApi

        val token = Utils.access
        val authHeader = "Bearer $token"

        val call = apiInterface.getQuizQuestions(authHeader, id)
        call.enqueue(object : Callback<List<Question>> {
            override fun onResponse(
                call: Call<List<Question>>,
                response: Response<List<Question>>
            ) {
                if (response.isSuccessful) {
                    val question = response.body()
                    if (question != null) {
                        onSuccess.invoke(question)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }

    fun submitQuiz(
        answers: MutableMap<String, Int>,
        id: Int,
        onSuccess: (QuizSubmissionResponse) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.mainApi

        val token = Utils.access
        val authHeader = "Bearer $token"

//        val answer = Answers(answers)
        val request = QuizSubmission(answers)

        val call = apiInterface.submitQuiz(authHeader, request, id)
        call.enqueue(object : Callback<QuizSubmissionResponse> {
            override fun onResponse(
                call: Call<QuizSubmissionResponse>,
                response: Response<QuizSubmissionResponse>
            ) {
                if (response.isSuccessful) {
                    val question = response.body()
                    if (question != null) {
                        onSuccess.invoke(question)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<QuizSubmissionResponse>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }

}