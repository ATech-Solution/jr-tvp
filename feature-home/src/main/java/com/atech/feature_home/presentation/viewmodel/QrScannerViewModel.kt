package com.atech.feature_home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.MessageModel
import com.atech.domain.interactors.PostQrCode
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrScannerViewModel @Inject constructor(
    private val postQrCode: PostQrCode
) : BaseViewModel() {

    private val _qrResponse = MutableLiveData<ResultState<MessageModel>>()
    val qrResponse: LiveData<ResultState<MessageModel>> = _qrResponse

    fun takeAttendance(scheduleId: Int, qrCode: String) {
        _qrResponse.value = ResultState.Loading()
        postQrCode.execute(
            object : DefaultSubscriber<MessageModel>() {
                override fun onError(error: ResultState<MessageModel>) {
                    _qrResponse.value = error
                }

                override fun onSuccess(data: ResultState<MessageModel>) {
                    _qrResponse.value = data
                }

            },
            PostQrCode.Params(
                scheduleId,
                qrCode
            )
        )
    }

    companion object {
        private const val TAG = "CameraXViewModel"
    }
}