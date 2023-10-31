package com.example.neoquizapp.model.mainModel

data class QuizSubmission(
    val answers: Answers
)

data class Answers(
    val questionIdToAnswerId: Map<Int, Int>
)

data class QuizSubmissionResponse(
    val quiz_id: Int,
    val correct_answers: Int
)
