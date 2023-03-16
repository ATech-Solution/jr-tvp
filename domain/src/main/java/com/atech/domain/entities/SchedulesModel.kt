package com.atech.domain.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SchedulesModel(
    val finish_time: String,
    val id: Int,
    val start_time: String,
    val title: String
): Parcelable