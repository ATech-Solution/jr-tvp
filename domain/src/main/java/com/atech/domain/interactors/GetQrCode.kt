package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.QrCodeModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class GetQrCode constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<QrCodeModel, GetQrCode.Params>(postExecutionThread) {

    class Params

    override fun build(params: Params): Flowable<QrCodeModel> {
        return repository.showQrCode()
    }

}