package com.atech.data.datasource

import com.atech.data.dtos.*
import io.reactivex.Flowable

interface DataSource {
    fun login(username: String, password: String): Flowable<LoginResponseDto>

    fun logout(): Flowable<MessageDto>

    fun getTeacherClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>>

    fun getStudents(id: Int): Flowable<BaseResponseDto<StudentsDto>>

    fun getStudentClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>>

    fun showQrCode(): Flowable<BaseResponseDto<QrCodeDto>>

    fun getProfile(): Flowable<BaseResponseDto<ProfileDto>>
}