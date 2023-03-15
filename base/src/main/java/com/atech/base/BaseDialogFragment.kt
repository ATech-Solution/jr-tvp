package com.atech.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

abstract class BaseDialogFragment<VB : ViewBinding, VM : ViewModel> : DialogFragment() {

    abstract val binding: VB
    abstract val viewModel: VM
    private var hasInitialized = false

    protected val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
        var isValid = true
        for (result in results) {
            if (!result.value) {
                isValid = false
            }
        }
        onPermissionGranted(isValid)
    }

    protected val photoPickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
        onFileChosen(result)
    }

    protected val documentPickerLauncher = registerForActivityResult(ActivityResultContracts.OpenDocument()) { result ->
        onFileChosen(result)
    }

    open fun onPermissionGranted(isGranted: Boolean) {}
    open fun onFileChosen(uri: Uri?) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val colorDrawable = ColorDrawable(Color.TRANSPARENT)
        val inset = InsetDrawable(colorDrawable, 40)
        dialog?.window?.setBackgroundDrawable(inset)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(binding.root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInitialized) {
            onInitObserver()
            onInitViews()
        }
    }

    override fun onPause() {
        super.onPause()
        hasInitialized = true
    }

    abstract fun onInitViews()
    abstract fun onInitObserver()

}