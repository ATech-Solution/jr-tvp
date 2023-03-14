package com.atech.data.datasource

import com.atech.data.dtos.LoginResponseDto
import com.atech.data.dtos.MessageDto
import io.reactivex.Flowable

interface DataSource {
    fun login(username: String, password: String): Flowable<LoginResponseDto>

    fun logout(): Flowable<MessageDto>
}