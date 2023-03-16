package com.atech.domain.interactors

import com.atech.domain.FlowableUseCase
import com.atech.domain.PostExecutionThread
import com.atech.domain.entities.ClassScheduleModel
import com.atech.domain.repositories.JrRepository
import io.reactivex.Flowable

class GetStudentClass constructor(
    private val repository: JrRepository,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<ClassScheduleModel, GetStudentClass.Params>(postExecutionThread) {

    class Params()

    override fun build(params: Params): Flowable<ClassScheduleModel> {
        return repository.getStudentClassSchedules()
    }

}