package com.atech.data.dtos

import com.atech.domain.entities.QrCodeModel

data class QrCodeDto(
    val qr_code: String?
) {
    fun toModel() = QrCodeModel(qr_code.orEmpty())
}