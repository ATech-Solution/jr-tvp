package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.MessageModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class PostLogout constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
): FlowableUseCase<MessageModel, PostLogout.Params>(postExecutionThread) {

    class Params

    override fun build(params: Params): Flowable<MessageModel> {
        return repository.logout()
    }

}