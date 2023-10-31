package com.example.neoquizapp.model

data class ProfileData(
    val user: UserInfo,
    val completed_quizzes: List<QuizProfile>
)

data class UserInfo(
    val id: Int,
    val username: String,
    val name: String
)

data class QuizProfile(
    val id: Int,
    val title: String
)

