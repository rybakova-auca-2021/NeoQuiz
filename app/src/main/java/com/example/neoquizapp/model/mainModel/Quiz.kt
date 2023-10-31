package com.example.neoquizapp.model.mainModel

data class QuizResult(
    val total_count: Int,
    val total_pages: Int,
    val list: List<Quiz>
)

data class Quiz(
    val id: Int,
    val title: String,
    val question_count: String,
    val is_completed: String
)
