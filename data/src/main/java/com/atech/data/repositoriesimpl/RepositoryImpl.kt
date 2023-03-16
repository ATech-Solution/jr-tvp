package com.atech.data.repositoriesimpl

import com.atech.data.datasource.DataSource
import com.atech.data.datasource.StubDataSource
import com.atech.domain.entities.*
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created by Abraham Lay on 2020-06-09.
 */
@Suppress("unused")
class RepositoryImpl @Inject constructor(
    private val source: DataSource,
    private val stubDataSource: StubDataSource
): JrRepository {
  
    var isUsingDummyData = false
    override fun login(username: String, password: String): Flowable<LoginResponseModel> {
        return source.login(username, password).map { it.toModel() }
    }

    override fun logout(): Flowable<MessageModel> {
        return source.logout().map { it.toModel() }
    }

    override fun getTeacherClassSchedules(): Flowable<ClassScheduleModel> {
        return source.getTeacherClassSchedules().map { it.data.toModel() }
    }

    override fun getStudents(id: Int): Flowable<StudentsModel> {
        return source.getStudents(id).map { it.data.toModel() }
    }

    override fun getStudentClassSchedules(): Flowable<ClassScheduleModel> {
        return source.getStudentClassSchedules().map { it.data.toModel() }
    }

    override fun showQrCode(): Flowable<QrCodeModel> {
        return source.showQrCode().map { it.data.toModel() }
    }

    override fun getProfile(): Flowable<ProfileModel> {
        return source.getProfile().map { it.data.toModel() }
    }

}