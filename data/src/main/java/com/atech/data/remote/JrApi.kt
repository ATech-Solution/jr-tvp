package com.atech.data.remote

import com.atech.data.dtos.LoginDto
import com.atech.data.dtos.LoginResponseDto
import com.atech.data.dtos.MessageDto
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface JrApi {

    @POST("/api/login")
    fun login(
        @Body loginDto: LoginDto
    ): Flowable<LoginResponseDto>

    @POST("/api/logout")
    fun logout(): Flowable<MessageDto>

}