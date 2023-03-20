package com.atech.feature_home.internal.di.module

import com.atech.domain.PostExecutionThread
import com.atech.domain.interactors.*
import com.atech.domain.repositories.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object HomeUseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideGetPopularMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetPopularMovies {
        return GetPopularMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNowPlayingMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetNowPlayingMovies {
        return GetNowPlayingMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTopRatedMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetTopRatedMovies {
        return GetTopRatedMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetFavoriteMovies {
        return GetFavoriteMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteMovie(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetFavoriteMovie {
        return GetFavoriteMovie(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertFavoriteMovie(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): InsertFavoriteMovie {
        return InsertFavoriteMovie(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteFavoriteMovie(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): DeleteFavoriteMovie {
        return DeleteFavoriteMovie(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetReviews(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetReviews {
        return GetReviews(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetVideos(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetVideos {
        return GetVideos(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDetailMovie(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetDetailMovie {
        return GetDetailMovie(movieRepository, postExecutionThread)
    }

}