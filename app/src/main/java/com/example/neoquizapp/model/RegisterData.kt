package com.example.neoquizapp.model

data class RegisterData(
    val username: String,
    val name: String,
    val password: String,
    val confirm_password: String
)

data class RegisterResponse(
    val message: String,
    val refresh: String,
    val access: String
)
