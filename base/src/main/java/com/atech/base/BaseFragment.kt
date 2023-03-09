package com.atech.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.atech.base.viewmodel.BaseViewModel

/**
 * Created by Abraham Lay on 2020-06-09.
 */
@SuppressLint("Registered")
abstract class BaseFragment<VB : ViewBinding,VM : BaseViewModel> : Fragment() {
    abstract val viewModel: VM
    abstract val binding: VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onInitViews()
        onInitObservers()
    }

    protected open fun onInitViews() = Unit

    protected open fun onInitObservers() = Unit
}