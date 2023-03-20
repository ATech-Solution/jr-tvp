package com.atech.android.di

import android.content.Context
import com.atech.android.MyApplication
import com.atech.base.config.WebApiProvider
import com.atech.data.datasource.DataSource
import com.atech.data.datasource.DataSourceImpl
import com.atech.data.datasource.StubDataSource
import com.atech.data.datasource.StubDataSourceImpl
import com.atech.data.remote.JrApi
import com.atech.data.repositoriesimpl.RepositoryImpl
import com.atech.domain.repositories.JrRepository
import com.atech.domain.util.SessionHelper
import com.atech.domain.util.StubUtil
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton
    @Provides
    fun provideStubDataSource(@ApplicationContext context: Context, stubUtil: StubUtil): StubDataSource =
        StubDataSourceImpl(context, stubUtil)

    @Singleton
    @Provides
    fun provideDataSource(api: JrApi, /*jrDao: JrDao,*/ sessionHelper: SessionHelper): DataSource =
        DataSourceImpl(
            api, /*jrDao,*/ sessionHelper
        )


    @Singleton
    @Provides
    fun provideRepository(source: DataSource, stubDataSource: StubDataSource): JrRepository =
        RepositoryImpl(
            source, stubDataSource
        )

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): JrApi = retrofit.create(
        JrApi::class.java
    )
}