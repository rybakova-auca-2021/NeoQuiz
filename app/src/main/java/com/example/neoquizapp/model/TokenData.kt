package com.example.neoquizapp.model

data class TokenData(
    val access: String,
    val refresh: String
)

data class TokenRefresh(
    val refresh: String
)

data class TokenRefreshResponse(
    val access: String
)
