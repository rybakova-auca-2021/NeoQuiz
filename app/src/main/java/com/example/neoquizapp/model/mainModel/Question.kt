package com.example.neoquizapp.model.mainModel

data class Question(
    val id: Int,
    val title: String,
    val answer: List<Answer>
)

data class Answer(
    val id: Int,
    val answer_text: String,
    val is_right: Boolean
)
