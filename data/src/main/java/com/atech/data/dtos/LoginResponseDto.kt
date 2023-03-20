package com.atech.data.dtos

import com.atech.domain.entities.LoginResponseModel

data class LoginResponseDto(
    val access_token: String,
    val message: String,
    val user_role: String
) {
    fun toModel(): LoginResponseModel {
        return LoginResponseModel(
            access_token, message, user_role
        )
    }
}