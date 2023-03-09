package com.atech.android.di

import com.atech.android.MyApplication
import com.atech.base.config.WebApiProvider
import com.atech.domain.util.SessionHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun provideWebApiProvider(): WebApiProvider = WebApiProvider

    @Singleton
    @Provides
    fun provideRetrofit(
        webApiProvider: WebApiProvider,
        myApplication: MyApplication,
        sessionHelper: SessionHelper
    ): Retrofit = webApiProvider.getRetrofit(myApplication.getBaseUrl(), myApplication.applicationContext, sessionHelper)

}