package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.MessageModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class PostQrCode constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<MessageModel, PostQrCode.Params>(postExecutionThread) {

    data class Params(
        val scheduleId: Int,
        val qrCode: String
    )

    override fun build(params: Params): Flowable<MessageModel> {
        return repository.scanQrCode(
            params.scheduleId,
            params.qrCode
        )
    }

}