package com.atech.data.dtos

import com.atech.domain.entities.SchedulesModel

data class SchedulesDto(
    val finish_time: String?,
    val id: Int?,
    val start_time: String?,
    val title: String?
) {
    fun toModel() = SchedulesModel(
        finish_time.orEmpty(),
        id ?: 0,
        start_time.orEmpty(),
        title.orEmpty()
    )
}