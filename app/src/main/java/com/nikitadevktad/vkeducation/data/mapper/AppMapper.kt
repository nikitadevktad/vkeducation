package com.nikitadevktad.vkeducation.data.mapper

import com.nikitadevktad.vkeducation.data.dto.AppDto
import com.nikitadevktad.vkeducation.domain.model.App

class AppMapper {
    fun toDomain(dto: AppDto): App = App(
        name = dto.name,
        description = dto.description,
        category = dto.category,
        iconUrl = dto.iconUrl,
    )
}
