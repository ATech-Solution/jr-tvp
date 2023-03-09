package com.atech.base.viewmodel

import androidx.lifecycle.ViewModel

/**
 * Created by Abraham Lay on 2019-10-06.
 */
abstract class BaseViewModel : ViewModel() {
    fun onComplete() {
        // No-op by default
    }

    fun onDataEmpty() {
        // No-op by default
    }

    fun resume() {
        // No-op by default
    }

    fun pause() {
        // No-op by default
    }

    fun destroy() {
        // No-op by default
    }
}