package com.atech.feature_home.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.atech.base.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.util.concurrent.ExecutionException
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class QrScannerViewModel @Inject constructor(
    @ApplicationContext
    private val context: Context
) : BaseViewModel() {

    private var cameraProviderLiveData: MutableLiveData<ProcessCameraProvider>? = null

    val processCameraProvider: LiveData<ProcessCameraProvider>
        get() {
            if (cameraProviderLiveData == null) {
                cameraProviderLiveData = MutableLiveData()
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener(
                    {
                        try {
                            cameraProviderLiveData?.setValue(cameraProviderFuture.get())
                        } catch (e: ExecutionException) {
                            // Handle any errors (including cancellation) here.
                            Timber.tag(TAG).e(e, "Unhandled exception")
                        } catch (e: InterruptedException) {
                            Timber.tag(TAG).e(e, "Unhandled exception")
                        }
                    },
                    ContextCompat.getMainExecutor(context)
                )
            }
            return cameraProviderLiveData!!
        }

    companion object {
        private const val TAG = "CameraXViewModel"
    }
}