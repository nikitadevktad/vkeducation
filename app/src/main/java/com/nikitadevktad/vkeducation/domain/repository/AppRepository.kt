package com.nikitadevktad.vkeducation.domain.repository

import com.nikitadevktad.vkeducation.domain.model.App

interface AppRepository {
    fun getApps(): List<App>
}
