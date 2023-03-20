package com.atech.data.dtos

import com.atech.domain.entities.StudentsModel

class StudentsDto : ArrayList<StudentsDtoItem>() {
    fun toModel() = StudentsModel().apply {
        addAll(this@StudentsDto.map { it.toModel() })
    }
}