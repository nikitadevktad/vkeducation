package com.nikitadevktad.vkeducation.data.mapper

import javax.inject.Inject
import com.nikitadevktad.vkeducation.data.dto.AppDto
import com.nikitadevktad.vkeducation.domain.model.App

class AppMapper @Inject constructor() {
    fun toDomain(dto: AppDto): App = App(
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl,
    )
}
