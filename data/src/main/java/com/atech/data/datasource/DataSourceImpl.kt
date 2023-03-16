package com.atech.data.datasource

import com.atech.data.dtos.*
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

    override fun getTeacherClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>> {
        return api.getTeacherClassSchedules()
    }

    override fun getStudents(id: Int): Flowable<BaseResponseDto<StudentsDto>> {
        return api.getStudents(id)
    }

    override fun getStudentClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>> {
        return api.getStudentClassSchedules()
    }

    override fun showQrCode(): Flowable<BaseResponseDto<QrCodeDto>> {
        return api.showQrCode()
    }

    override fun getProfile(): Flowable<BaseResponseDto<ProfileDto>> {
        return api.getProfile()
    }
}