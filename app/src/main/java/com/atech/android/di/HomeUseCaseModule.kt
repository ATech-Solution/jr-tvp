package com.atech.android.di

import com.atech.domain.PostExecutionThread
import com.atech.domain.interactors.*
import com.atech.domain.repositories.JrRepository
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
    fun provideGetTeacherClass(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): GetTeacherClass {
        return GetTeacherClass(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetStudentClass(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): GetStudentClass {
        return GetStudentClass(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetStudents(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): GetStudents {
        return GetStudents(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetQrCode(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): GetQrCode {
        return GetQrCode(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetProfile(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): GetProfile {
        return GetProfile(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun providePostQrCode(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): PostQrCode {
        return PostQrCode(repository, postExecutionThread)
    }
}