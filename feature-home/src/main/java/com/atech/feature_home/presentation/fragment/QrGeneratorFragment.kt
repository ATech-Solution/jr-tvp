package com.atech.feature_home.presentation.fragment

import android.graphics.Bitmap
import android.util.DisplayMetrics
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.fragment.app.viewModels
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.feature_home.databinding.FragmentQrGeneratorBinding
import com.atech.feature_home.presentation.viewmodel.QrGeneratorViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.FragmentScoped
import timber.log.Timber

@FragmentScoped
@AndroidEntryPoint
class QrGeneratorFragment : BaseFragment<FragmentQrGeneratorBinding, BaseViewModel>() {

    override val viewModel: QrGeneratorViewModel by viewModels()
    override val binding: FragmentQrGeneratorBinding by lazy {
        FragmentQrGeneratorBinding.inflate(layoutInflater)
    }

    override fun onInitViews() {
        super.onInitViews()
        generateQrCode("text barcode")?.let {
            binding.imgQrCode.setImageBitmap(it)
        }
    }

    @Suppress("deprecation")
    private fun generateQrCode(inputValue: String): Bitmap? {
        val displayMetrics = DisplayMetrics().also {
            requireActivity().windowManager.defaultDisplay.getMetrics(it)
        }
        val dimension = displayMetrics.widthPixels / 2
        val qrgEncoder = QRGEncoder(inputValue, null, QRGContents.Type.TEXT, dimension)
        return try {
            qrgEncoder.bitmap
        } catch (ex: Exception) {
            Timber.tag(TAG).e(ex.message.orEmpty())
            null
        }
    }

    override fun onInitObservers() {
        super.onInitObservers()
    }

    companion object {
        private val TAG = QrGeneratorFragment::class.java.simpleName
    }
}