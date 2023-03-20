package com.atech.feature_home.presentation.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.RectF
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.subscriber.ResultState
import com.atech.feature_home.databinding.FragmentQrScannerBinding
import com.atech.feature_home.presentation.view.BarcodeBoxView
import com.atech.feature_home.presentation.viewmodel.QrScannerViewModel
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import timber.log.Timber
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@FragmentScoped
@AndroidEntryPoint
class QrScannerFragment : BaseFragment<FragmentQrScannerBinding, BaseViewModel>(), ImageAnalysis.Analyzer {

    override val viewModel: QrScannerViewModel by viewModels()

    override val binding: FragmentQrScannerBinding by lazy {
        FragmentQrScannerBinding.inflate(layoutInflater)
    }

    private lateinit var cameraExecutor: ExecutorService

    private val args: QrScannerFragmentArgs by navArgs()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startCamera()
        } else {
            Timber.tag(TAG).e("no camera permission")
        }
    }

    private var scaleX = 1f
    private var scaleY = 1f

    private fun translateX(x: Float) = x * scaleX
    private fun translateY(y: Float) = y * scaleY

    private val barcodeBoxView by lazy {
        BarcodeBoxView(requireContext())
    }

    override fun onInitViews() {
        super.onInitViews()
        if (isCameraPermissionGranted()) {
            startCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun startCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            // Preview
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            // Image analyzer
            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, this)
                }

            // Select back camera as a default
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageAnalyzer
                )

            } catch (exc: Exception) {
                exc.printStackTrace()
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun adjustBoundingRect(rect: Rect) = RectF(
        translateX(rect.left.toFloat()),
        translateY(rect.top.toFloat()),
        translateX(rect.right.toFloat()),
        translateY(rect.bottom.toFloat())
    )

    @SuppressLint("UnsafeOptInUsageError")
    override fun analyze(image: ImageProxy) {
        image.image?.let {
            val inputImage = InputImage.fromMediaImage(it, image.imageInfo.rotationDegrees)

            val options = BarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                    Barcode.FORMAT_QR_CODE
                )
                .build()
            val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(options)

            scaleX = binding.previewView.width.toFloat() / it.height.toFloat()
            scaleY = binding.previewView.height.toFloat() / it.width.toFloat()

            barcodeScanner.process(inputImage)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        barcodes.last().rawValue?.let { code ->
                            if (binding.pbLoading.visibility == View.GONE) {
                                viewModel.takeAttendance(
                                    args.scheduleId,
                                    code
                                )
                            }
                        }

                        barcodes.forEach { code ->
                            code.boundingBox?.let { rect ->
                                barcodeBoxView.setRect(
                                    adjustBoundingRect(
                                        rect
                                    )
                                )
                            }
                        }
                    } else {
                        barcodeBoxView.setRect(RectF())
                    }
                }
                .addOnFailureListener { ex ->
                    Timber.tag(TAG).e(ex.message.orEmpty())
                }.addOnCompleteListener {
                    // When the image is from CameraX analysis use case, must call image.close() on received
                    // images when finished using them. Otherwise, new images may not be received or the camera
                    // may stall.
                    image.close()
                }
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onInitObservers() {
        super.onInitObservers()
        viewModel.qrResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.data.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Error -> {
                    binding.pbLoading.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        it.throwable.message,
                        Toast.LENGTH_SHORT)
                        .show()
                }
                is ResultState.Loading -> {
                    binding.pbLoading.visibility = View.VISIBLE
                }
                else -> {
                    // unhandled state
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private val TAG = QrScannerFragment::class.java.simpleName
    }

}