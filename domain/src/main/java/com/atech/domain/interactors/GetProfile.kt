package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.ProfileModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class GetProfile constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<ProfileModel, GetProfile.Params>(postExecutionThread) {

    class Params

    override fun build(params: Params): Flowable<ProfileModel> {
        return repository.getProfile()
    }

}