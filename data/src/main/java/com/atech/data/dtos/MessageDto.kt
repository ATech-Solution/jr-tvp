package com.atech.data.dtos

import com.atech.domain.entities.MessageModel

data class MessageDto(
    val message: String
) {
    fun toModel(): MessageModel {
        return MessageModel(message)
    }
}