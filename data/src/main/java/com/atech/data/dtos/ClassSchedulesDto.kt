package com.atech.data.dtos

import com.atech.domain.entities.ClassScheduleModel

class ClassSchedulesDto : ArrayList<ClassSchedulesDtoItem>() {
    fun toModel() = ClassScheduleModel().apply {
        addAll(this@ClassSchedulesDto.map { it.toModel() })
    }
}