package com.nikitadevktad.vkeducation.data.source

import kotlinx.coroutines.delay
import javax.inject.Inject

class FakeAppApi @Inject constructor() : AppApi {
    override suspend fun getRating(id: String): Float {
        delay(1_500)
        return (30..50).random() / 10f
    }
}
