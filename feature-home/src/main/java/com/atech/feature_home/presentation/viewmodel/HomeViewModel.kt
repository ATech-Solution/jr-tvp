package com.atech.feature_home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.ClassScheduleModel
import com.atech.domain.entities.LoginResponseModel
import com.atech.domain.entities.ProfileModel
import com.atech.domain.interactors.GetProfile
import com.atech.domain.interactors.GetStudentClass
import com.atech.domain.interactors.GetTeacherClass
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import com.atech.domain.util.SessionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfile: GetProfile,
    private val getTeacherClass: GetTeacherClass,
    private val getStudentClass: GetStudentClass,
    private val sessionHelper: SessionHelper
) : BaseViewModel() {

    private val _profileResponse = MutableLiveData<ResultState<ProfileModel>>()
    val profileResponse: LiveData<ResultState<ProfileModel>> = _profileResponse

    private val _classResponse = MutableLiveData<ResultState<ClassScheduleModel>>()
    val classResponse: LiveData<ResultState<ClassScheduleModel>> = _classResponse

    init {
        getProfile()
        checkLoginState()
    }

    private fun getProfile() {
        _profileResponse.value = ResultState.Loading()
        getProfile.execute(
            object : DefaultSubscriber<ProfileModel>() {
                override fun onError(error: ResultState<ProfileModel>) {
                    _profileResponse.value = error
                }

                override fun onSuccess(data: ResultState<ProfileModel>) {
                    sessionHelper.saveObject(
                        SessionHelper.USER_PROFILE,
                        (data as ResultState.Success).data
                    )
                    _profileResponse.value = data
                }

            },
            GetProfile.Params()
        )
    }

    fun checkLoginState() {
        val loginResponse = sessionHelper.getObject(
            SessionHelper.LOGIN_RESPONSE,
            LoginResponseModel::class.java
        )
        if (loginResponse != null) {
            if (loginResponse.user_role == "student") {
                getStudentClass()
            } else { // teacher
                getTeacherClass()
            }
        }
    }

    private fun getStudentClass() {
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

    private fun getTeacherClass() {
        _classResponse.value = ResultState.Loading()
        getTeacherClass.execute(
            object : DefaultSubscriber<ClassScheduleModel>() {
                override fun onError(error: ResultState<ClassScheduleModel>) {
                    _classResponse.value = error
                }

                override fun onSuccess(data: ResultState<ClassScheduleModel>) {
                    _classResponse.value = data
                }

            },
            GetTeacherClass.Params()
        )
    }

}