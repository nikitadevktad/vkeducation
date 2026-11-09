package com.nikitadevktad.vkeducation.data.repository

import javax.inject.Inject

class GreetingRepositoryImpl @Inject constructor() : GreetingRepository {
    override fun getName(): String = "Android"
}
