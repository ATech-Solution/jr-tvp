package com.atech.data.dtos

import com.atech.domain.entities.ProfileModel

data class ProfileDto(
    val address: String?,
    val avatar: String?,
    val country_code: String?,
    val email: String?,
    val id: Int?,
    val name: String?,
    val phone: String?,
    val registered: String?
) {
    fun toModel() = ProfileModel(
        address.orEmpty(),
        avatar.orEmpty(),
        country_code.orEmpty(),
        email.orEmpty(),
        id ?: 0,
        name.orEmpty(),
        phone.orEmpty(),
        registered.orEmpty()
    )
}