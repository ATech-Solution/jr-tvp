package com.atech.feature_home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.entities.QrCodeModel
import com.atech.domain.interactors.GetQrCode
import com.atech.domain.subscriber.DefaultSubscriber
import com.atech.domain.subscriber.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QrGeneratorViewModel @Inject constructor(
    private val getQrCode: GetQrCode
) : BaseViewModel() {

    private val _qrResponse = MutableLiveData<ResultState<QrCodeModel>>()
    val qrResponse: LiveData<ResultState<QrCodeModel>> = _qrResponse

    init {
        getQrCode()
    }

    private fun getQrCode() {
        _qrResponse.value = ResultState.Loading()
        getQrCode.execute(
            object : DefaultSubscriber<QrCodeModel>() {
                override fun onError(error: ResultState<QrCodeModel>) {
                    _qrResponse.value = error
                }

                override fun onSuccess(data: ResultState<QrCodeModel>) {
                    _qrResponse.value = data
                }

            },
            GetQrCode.Params()
        )
    }

}