package com.example.neoquizapp.model.mainModel

data class QuizSubmission(
    val answers: Map<String, Int>
)

data class Answers(
    val answers: Map<String, Int>
)

data class QuizSubmissionResponse(
    val quiz_id: Int,
    val correct_answers: Int
)
