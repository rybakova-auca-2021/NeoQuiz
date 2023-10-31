package com.example.neoquizapp.model.mainModel

data class ArticleResult(
    val total_count: Int,
    val total_pages: Int,
    val list: List<Article>
)
data class Article(
    val id: Int,
    val title: String,
    val text: String,
    val category: Category,
    val reading_time_minutes: Int
)

data class Category(
    val id: Int,
    val name: String
)
