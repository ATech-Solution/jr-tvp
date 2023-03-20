package com.atech.data.dtos

import com.atech.domain.entities.ClassScheduleModelItem

data class ClassSchedulesDtoItem(
    val category: String?,
    val duration: Int?,
    val id: Int?,
    val location: String?,
    val remaining_class: Int?,
    val schedules: List<SchedulesDto>?,
    val teacher: String?,
    val time_summary: String?
) {
    fun toModel() = ClassScheduleModelItem(
        category.orEmpty(),
        duration ?: 0,
        id ?: 0,
        location.orEmpty(),
        remaining_class ?: 0,
        schedules?.map { it.toModel() } ?: listOf(),
        teacher.orEmpty(),
        time_summary.orEmpty()
    )
}