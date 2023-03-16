package com.atech.data.dtos

import com.atech.domain.entities.StudentsModelItem

data class StudentsDtoItem(
    val id: Int?,
    val name: String?
) {
    fun toModel() = StudentsModelItem(
        id ?: 0,
        name.orEmpty()
    )
}