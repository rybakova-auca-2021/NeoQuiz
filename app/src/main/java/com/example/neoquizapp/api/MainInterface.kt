package com.example.neoquizapp.api

import com.example.neoquizapp.model.mainModel.Article
import com.example.neoquizapp.model.mainModel.ArticleResult
import com.example.neoquizapp.model.mainModel.Question
import com.example.neoquizapp.model.mainModel.Quiz
import com.example.neoquizapp.model.mainModel.QuizDetail
import com.example.neoquizapp.model.mainModel.QuizResult
import com.example.neoquizapp.model.mainModel.QuizSubmission
import com.example.neoquizapp.model.mainModel.QuizSubmissionResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MainInterface {
    @GET("content/articles/")
    fun getArticles(
        @Header("Authorization") token: String,
        @Query("search") search: String? = null,
        @Query("category") category: Int? = null
    ): Call<ArticleResult>

    @GET("content/quizzes/")
    fun getQuizzes(@Header("Authorization") token: String): Call<QuizResult>

    @GET("content/quizzes/{id}/")
    fun getQuiz(@Header("Authorization") token: String, @Path("id") id: Int): Call<QuizDetail>

    @GET("content/quizzes/{id}/questions/")
    fun getQuizQuestions(@Path("id") id: Int): Call<Question>

    @POST("content/quizzes/{id}/submit/")
    fun submitQuiz(@Body request: QuizSubmission, @Path("id") id: Int): Call<QuizSubmissionResponse>
}