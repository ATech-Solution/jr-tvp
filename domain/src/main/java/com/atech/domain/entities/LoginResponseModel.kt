package com.atech.domain.entities

data class LoginResponseModel(
    val access_token: String,
    val message: String,
    val user_role: String
)
