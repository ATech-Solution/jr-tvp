package com.atech.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ClassScheduleModelItem(
    val category: String,
    val duration: Int,
    val id: Int,
    val location: String,
    val remaining_class: Int,
    val schedules: List<SchedulesModel>,
    val teacher: String,
    val time_summary: String
): Parcelable