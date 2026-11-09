package com.nikitadevktad.vkeducation.data.mapper

import com.nikitadevktad.vkeducation.data.dto.ApplicationDetailsDto
import com.nikitadevktad.vkeducation.data.dto.CatalogAppDto
import com.nikitadevktad.vkeducation.domain.model.ApplicationDetails
import com.nikitadevktad.vkeducation.domain.model.CatalogApplication

fun CatalogAppDto.toDomain(): CatalogApplication = CatalogApplication(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl,
)

fun ApplicationDetailsDto.toDomain(): ApplicationDetails = ApplicationDetails(
    id = id,
    name = name,
    developer = developer,
    category = category,
    ageRating = ageRating,
    size = size,
    iconUrl = iconUrl,
    screenshotUrlList = screenshotUrlList,
    description = description,
)
