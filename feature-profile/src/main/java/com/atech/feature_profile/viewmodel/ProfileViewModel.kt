package com.atech.feature_profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.MessageModel
import com.atech.domain.entities.ProfileModel
import com.atech.domain.interactors.GetProfile
import com.atech.domain.interactors.PostLogout
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import com.atech.domain.util.SessionHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfile: GetProfile,
    private val postLogout: PostLogout,
    private val sessionHelper: SessionHelper
): BaseViewModel() {

    private val _profileResponse = MutableLiveData<ResultState<ProfileModel>>()
    val profileResponse: LiveData<ResultState<ProfileModel>> = _profileResponse

    private val _logoutResponse = MutableLiveData<ResultState<MessageModel>>()
    val logoutResponse: LiveData<ResultState<MessageModel>> = _logoutResponse

    init {
        getProfile()
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


    fun logout() {
        _logoutResponse.value = ResultState.Loading()
        postLogout.execute(object : DefaultSubscriber<MessageModel>() {
            override fun onError(error: ResultState<MessageModel>) {
                _logoutResponse.value = error
            }

            override fun onSuccess(data: ResultState<MessageModel>) {
                sessionHelper.remove(SessionHelper.ACCESS_TOKEN_KEY)
                sessionHelper.remove(SessionHelper.LOGIN_RESPONSE)
                _logoutResponse.value = data
            }

        },
            PostLogout.Params()
        )
    }

}