package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.StudentsModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class GetStudents constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<StudentsModel, GetStudents.Params>(postExecutionThread) {

    data class Params(val id: Int)

    override fun build(params: Params): Flowable<StudentsModel> {
        return repository.getStudents(params.id)
    }

}