package com.example.neoquizapp.viewModel.MainViewModel

import androidx.lifecycle.ViewModel
import com.example.neoquizapp.api.RetrofitInstance
import com.example.neoquizapp.model.mainModel.Article
import com.example.neoquizapp.model.mainModel.ArticleResult
import com.example.neoquizapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetArticlesViewModel : ViewModel() {
    private val apiInterface = RetrofitInstance.mainApi
    private val token = Utils.access
    private val authHeader = "Bearer $token"

    // Function with only search parameter
    fun getArticlesBySearch(
        search: String,
        onSuccess: (List<Article>) -> Unit
    ) {
        apiInterface.getArticles(authHeader, search = search)
            .enqueue(getCallback(onSuccess))
    }

    // Function with only category parameter
    fun getArticlesByCategory(
        category: Int,
        onSuccess: (List<Article>) -> Unit
    ) {
        apiInterface.getArticles(authHeader, category = category)
            .enqueue(getCallback(onSuccess))
    }

    // Function without search or category parameters
    fun getAllArticles(
        onSuccess: (List<Article>) -> Unit
    ) {
        apiInterface.getArticles(authHeader)
            .enqueue(getCallback(onSuccess))
    }

    private fun getCallback(onSuccess: (List<Article>) -> Unit): Callback<ArticleResult> {
        return object : Callback<ArticleResult> {
            override fun onResponse(call: Call<ArticleResult>, response: Response<ArticleResult>) {
                if (response.isSuccessful) {
                    val articleBody = response.body()
                    if (articleBody != null) {
                        onSuccess.invoke(articleBody.list)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ArticleResult>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        }
    }
}
