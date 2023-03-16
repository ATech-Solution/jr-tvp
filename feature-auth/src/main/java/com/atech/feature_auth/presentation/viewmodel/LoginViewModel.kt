package com.atech.feature_auth.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.LoginResponseModel
import com.atech.domain.interactors.PostLogin
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import com.atech.domain.util.SessionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class LoginViewModel @Inject constructor(
    private val postLogin: PostLogin,
    private val sessionHelper: SessionHelper
) : BaseViewModel() {

    private val _loginResponse = MutableLiveData<ResultState<LoginResponseModel>>()
    val loginResponse: LiveData<ResultState<LoginResponseModel>> = _loginResponse

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        val loginResponse = sessionHelper.getObject(
            SessionHelper.LOGIN_RESPONSE,
            LoginResponseModel::class.java
        )
        if (loginResponse != null) {
            _loginResponse.value = ResultState.Success(loginResponse)
        }
    }

    fun login(username: String, password: String) {
        _loginResponse.value = ResultState.Loading()
        postLogin.execute(
            object : DefaultSubscriber<LoginResponseModel>() {
                override fun onError(error: ResultState<LoginResponseModel>) {
                    _loginResponse.value = error
                }

                override fun onSuccess(data: ResultState<LoginResponseModel>) {
                    val loginResponse = (data as ResultState.Success).data
                    sessionHelper.saveString(
                        SessionHelper.ACCESS_TOKEN_KEY,
                        loginResponse.access_token
                    )
                    sessionHelper.saveObject(
                        SessionHelper.LOGIN_RESPONSE,
                        loginResponse
                    )
                    _loginResponse.value = data
                }

            },
            PostLogin.Params(
                username,
                password
            )
        )
    }

}

