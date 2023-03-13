package com.atech.feature_home.presentation.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.DisplayMetrics
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.feature_home.databinding.FragmentQrScannerBinding
import com.atech.feature_home.presentation.viewmodel.QrScannerViewModel
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import timber.log.Timber
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


@FragmentScoped
@AndroidEntryPoint
class QrScannerFragment : BaseFragment<FragmentQrScannerBinding, BaseViewModel>() {

    override val viewModel: QrScannerViewModel by viewModels()

    override val binding: FragmentQrScannerBinding by lazy {
        FragmentQrScannerBinding.inflate(layoutInflater)
    }

    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var cameraSelector: CameraSelector
    private var cameraUseCase: Preview? = null
    private var analysisUseCase: ImageAnalysis? = null
    @Suppress("deprecation")
    private val screenAspectRatio: Int
        get() {
            // Get screen metrics used to setup camera for full screen resolution
            val metrics = DisplayMetrics().also { binding.previewView.display?.getRealMetrics(it) }
            return aspectRatio(metrics.widthPixels, metrics.heightPixels)
        }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    override fun onInitViews() {
        super.onInitViews()
        if (isCameraPermissionGranted()) {
            setupCamera()
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA_REQUEST
            )
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
    }

    private fun setupCamera() {
        val lensFacing = CameraSelector.LENS_FACING_BACK
        cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        viewModel.processCameraProvider.observe(this) {
            cameraProvider = it
            if (isCameraPermissionGranted()) {
                bindCameraUseCases()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_CAMERA_REQUEST
                )
            }
        }
    }

    private fun bindCameraUseCases() {
        bindPreviewUseCase()
        bindAnalyzeUseCase()
    }

    private fun bindPreviewUseCase() {
        if (!::cameraProvider.isInitialized) {
            return
        }
        if (cameraUseCase != null) {
            cameraProvider.unbind(cameraUseCase)
        }

        cameraUseCase = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(binding.previewView.display.rotation)
            .build()
        cameraUseCase?.setSurfaceProvider(binding.previewView.createSurfaceProvider())

        try {
            cameraProvider.bindToLifecycle(this,
                cameraSelector,
                cameraUseCase
            )
        } catch (illegalStateException: IllegalStateException) {
            Timber.tag(TAG).e(illegalStateException.message.orEmpty())
        } catch (illegalArgumentException: IllegalArgumentException) {
            Timber.tag(TAG).e(illegalArgumentException.message.orEmpty())
        }
    }

    private fun bindAnalyzeUseCase() {
        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient()
        if (!::cameraProvider.isInitialized) {
            return
        }
        if (analysisUseCase != null) {
            cameraProvider.unbind(analysisUseCase)
        }

        analysisUseCase = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(binding.previewView.display.rotation)
            .build()

        // Initialize our background executor
        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(
            cameraExecutor
        ) { imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }

        try {
            cameraProvider.bindToLifecycle(
                this,
                cameraSelector,
                analysisUseCase
            )
        } catch (illegalStateException: IllegalStateException) {
            Timber.tag(TAG).e(
                illegalStateException.message ?: "IllegalStateException"
            )
        } catch (illegalArgumentException: IllegalArgumentException) {
            Timber.tag(TAG).e(
                illegalArgumentException.message ?: "IllegalArgumentException"
            )
        }
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ) {
        imageProxy.image?.let {
            val inputImage = InputImage.fromMediaImage(it, imageProxy.imageInfo.rotationDegrees)

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    barcodes.forEach { code ->
                        Timber.tag(TAG).d(code.rawValue.orEmpty())
                    }
                }
                .addOnFailureListener { ex ->
                    Timber.tag(TAG).e(ex.message.orEmpty())
                }.addOnCompleteListener {
                    // When the image is from CameraX analysis use case, must call image.close() on received
                    // images when finished using them. Otherwise, new images may not be received or the camera
                    // may stall.
                    imageProxy.close()
                }
        }
    }

    @Suppress("deprecation")
    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_CAMERA_REQUEST) {
            if (isCameraPermissionGranted()) {
                bindCameraUseCases()
            } else {
                Timber.tag(TAG).e("no camera permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private val TAG = QrScannerFragment::class.java.simpleName
        private const val PERMISSION_CAMERA_REQUEST = 1
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }
}