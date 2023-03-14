package com.atech.feature_home.internal.di.module

import android.content.Context
import com.atech.data.datasource.*
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
object HomeApiModule {
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