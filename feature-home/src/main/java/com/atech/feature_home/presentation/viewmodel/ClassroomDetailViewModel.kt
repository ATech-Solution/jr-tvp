package com.atech.feature_home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.ClassScheduleModel
import com.atech.domain.entities.LoginResponseModel
import com.atech.domain.entities.StudentsModel
import com.atech.domain.interactors.GetQrCode
import com.atech.domain.interactors.GetStudentClass
import com.atech.domain.interactors.GetStudents
import com.atech.domain.interactors.GetTeacherClass
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import com.atech.domain.util.SessionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ClassroomDetailViewModel @Inject constructor(
    private val getTeacherClass: GetTeacherClass,
    private val getStudentClass: GetStudentClass,
    private val getStudents: GetStudents,
    private val sessionHelper: SessionHelper
) : BaseViewModel() {

    private val _studentsResponse = MutableLiveData<ResultState<StudentsModel>>()
    val studentsResponse: LiveData<ResultState<StudentsModel>> = _studentsResponse
    var isStudent = true

    private val _classResponse = MutableLiveData<ResultState<ClassScheduleModel>>()
    val classResponse: LiveData<ResultState<ClassScheduleModel>> = _classResponse

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        val loginResponse = sessionHelper.getObject(
            SessionHelper.LOGIN_RESPONSE,
            LoginResponseModel::class.java
        )
        if (loginResponse != null) {
            isStudent = loginResponse.user_role == "student"
        }
    }

    fun getStudents(id: Int) {
        _studentsResponse.value = ResultState.Loading()
        getStudents.execute(
            object : DefaultSubscriber<StudentsModel>() {
                override fun onError(error: ResultState<StudentsModel>) {
                    _studentsResponse.value = error
                }

                override fun onSuccess(data: ResultState<StudentsModel>) {
                    _studentsResponse.value = data
                }

            },
            GetStudents.Params(
                id
            )
        )
    }

    fun getStudentClass() {
        _classResponse.value = ResultState.Loading()
        getStudentClass.execute(
            object : DefaultSubscriber<ClassScheduleModel>() {
                override fun onError(error: ResultState<ClassScheduleModel>) {
                    _classResponse.value = error
                }

                override fun onSuccess(data: ResultState<ClassScheduleModel>) {
                    _classResponse.value = data
                }

            },
            GetStudentClass.Params()
        )
    }

}