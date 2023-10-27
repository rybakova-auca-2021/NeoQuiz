package com.example.neoquizapp.api

import com.example.neoquizapp.model.ChangePassword
import com.example.neoquizapp.model.LoginData
import com.example.neoquizapp.model.PasswordResetRequest
import com.example.neoquizapp.model.ProfileData
import com.example.neoquizapp.model.RegisterData
import com.example.neoquizapp.model.ResponseMessage
import com.example.neoquizapp.model.TokenData
import com.example.neoquizapp.model.TokenRefresh
import com.example.neoquizapp.model.TokenRefreshResponse
import com.example.neoquizapp.model.VerifyCodeData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthInterface {
    @PUT("users/change_password/")
    fun changePassword(@Body request: ChangePassword): Call<ResponseMessage>

    @POST("users/login/")
    fun login(@Body request: LoginData): Call<TokenData>

    @POST("users/login/refresh/")
    fun refreshToken(@Body request: TokenRefresh): Call<TokenRefreshResponse>

    @POST("users/password_reset_request/")
    fun requestPasswordReset(@Body request: PasswordResetRequest) : Call<ResponseMessage>

    @POST("users/register/")
    fun registerUser(@Body request: RegisterData) : Call<ResponseMessage>

    @POST("users/verify_code/")
    fun verifyCode(@Body request: VerifyCodeData): Call<ResponseMessage>

    @GET("users/profile/")
    fun getProfile(): Call<ProfileData>
}