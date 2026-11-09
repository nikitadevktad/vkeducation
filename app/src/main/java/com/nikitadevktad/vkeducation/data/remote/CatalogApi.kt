package com.nikitadevktad.vkeducation.data.remote

import com.nikitadevktad.vkeducation.data.dto.ApplicationDetailsDto
import com.nikitadevktad.vkeducation.data.dto.CatalogAppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApi {
    @GET("catalog")
    suspend fun getCatalog(): List<CatalogAppDto>

    @GET("{id}")
    suspend fun getApplication(@Path("id") id: String): ApplicationDetailsDto
}
