package com.atech.data.remote

import com.atech.data.dtos.*
import io.reactivex.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface JrApi {

    @POST("/api/login")
    fun login(
        @Body loginDto: LoginDto
    ): Flowable<LoginResponseDto>

    @POST("/api/logout")
    fun logout(): Flowable<MessageDto>

    @GET("/api/teacher/class-schedules")
    fun getTeacherClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>>

    @GET("/api/teacher/class-schedules/{id}")
    fun getStudents(
        @Path("id") id: Int
    ): Flowable<BaseResponseDto<StudentsDto>>

    @GET("/api/student/class-schedules")
    fun getStudentClassSchedules(): Flowable<BaseResponseDto<ClassSchedulesDto>>

    @GET("/api/student/qr-code")
    fun showQrCode(): Flowable<BaseResponseDto<QrCodeDto>>

    @GET("/api/profile")
    fun getProfile(): Flowable<BaseResponseDto<ProfileDto>>

}