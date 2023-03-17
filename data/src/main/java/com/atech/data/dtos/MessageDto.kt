package com.atech.data.dtos

import com.atech.domain.entities.MessageModel

data class MessageDto(
    val message: String?,
    val is_success: Boolean?
) {
    fun toModel(): MessageModel {
        return MessageModel(message.orEmpty(), is_success ?: true)
    }
}