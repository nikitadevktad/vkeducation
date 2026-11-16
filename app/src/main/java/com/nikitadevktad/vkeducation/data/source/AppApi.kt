package com.nikitadevktad.vkeducation.data.source

interface AppApi {
    suspend fun getRating(id: String): Float
}
