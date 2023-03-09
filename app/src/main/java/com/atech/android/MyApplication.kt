package com.atech.android

import com.atech.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : BaseApplication() {
    override fun getBaseUrl(): String = BuildConfig.API_BASE_URL
}