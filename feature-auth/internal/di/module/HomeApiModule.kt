package com.atech.feature_home.internal.di.module

import android.content.Context
import com.atech.data.datasource.MovieDataSourceImpl
import com.atech.data.datasource.StubDataSourceImpl
import com.atech.data.db.MovieDao
import com.atech.data.mapper.DetailMovieMapper
import com.atech.data.mapper.MovieMapper
import com.atech.data.mapper.ReviewMapper
import com.atech.data.mapper.VideoMapper
import com.atech.data.remote.MovieApi
import com.atech.data.repositoriesimpl.MovieRepositoryImpl
import com.atech.data.datasource.MovieDataSource
import com.atech.data.datasource.StubDataSource
import com.atech.domain.repositories.MovieRepository
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
    fun provideMovieDataSource(movieApi: MovieApi, movieDao: MovieDao, sessionHelper: SessionHelper): MovieDataSource =
        MovieDataSourceImpl(
            movieApi, movieDao, sessionHelper
        )


    @Singleton
    @Provides
    fun provideMovieRepository(source: MovieDataSource, stubDataSource: StubDataSource): MovieRepository =
        MovieRepositoryImpl(
            source, stubDataSource, MovieMapper(), ReviewMapper(),
            VideoMapper(), DetailMovieMapper()
        )

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(
        MovieApi::class.java
    )

}