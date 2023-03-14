package com.atech.data.datasource

import com.atech.data.dtos.LoginDto
import com.atech.data.dtos.LoginResponseDto
import com.atech.data.dtos.MessageDto
import com.atech.data.remote.JrApi
import com.atech.domain.util.SessionHelper
import io.reactivex.Flowable
import javax.inject.Inject

@Suppress("unused")
class DataSourceImpl @Inject constructor(
    private val api: JrApi,
   /*private val jrDao: JrDao,*/
    private val sessionHelper: SessionHelper
) : DataSource {
    override fun login(username: String, password: String): Flowable<LoginResponseDto> {
        return api.login(LoginDto(username, password))
    }

    override fun logout(): Flowable<MessageDto> {
        return api.logout()
    }
}