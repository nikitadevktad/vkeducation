package com.nikitadevktad.vkeducation.data.dto

data class ApplicationDetailsDto(
    val id: String,
    val name: String,
    val developer: String,
    val category: String,
    val ageRating: Int,
    val size: Double,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)
