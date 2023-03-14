package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.LoginResponseModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class PostLogin constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<LoginResponseModel, PostLogin.Params>(postExecutionThread) {

    data class Params(
        val username: String,
        val password: String
        )

    override fun build(params: Params): Flowable<LoginResponseModel> {
        return repository.login(params.username, params.password)
    }

}