package com.atech.feature_home.presentation.fragment

import android.graphics.Bitmap
import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.atech.base.BaseFragment
import com.atech.base.viewmodel.BaseViewModel
import com.atech.domain.subscriber.ResultState
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
        val callback: OnBackPressedCallback = object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setFragmentResult(
                    ClassroomDetailFragment.REQUEST_KEY,
                    bundleOf(Pair(ClassroomDetailFragment.REQUEST_KEY, true))
                )
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        viewModel.qrResponse.observe(viewLifecycleOwner) {
            when(it) {
                is ResultState.Success -> {
                    binding.pbLoading.visibility = View.GONE
                    generateQrCode(it.data.qr_code)?.let { bitmap ->
                        binding.imgQrCode.setImageBitmap(bitmap)
                    }
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
                    //unhandled state
                }
            }
        }
    }

    companion object {
        private val TAG = QrGeneratorFragment::class.java.simpleName
    }
}