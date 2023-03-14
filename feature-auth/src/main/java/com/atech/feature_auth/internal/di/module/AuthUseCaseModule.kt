package com.atech.feature_auth.internal.di.module

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
object AuthUseCaseModule {

    @Provides
    @ViewModelScoped
    fun providePostLogin(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): PostLogin {
        return PostLogin(repository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun providePostLogout(
        repository: JrRepository,
        postExecutionThread: PostExecutionThread
    ): PostLogout {
        return PostLogout(repository, postExecutionThread)
    }

}