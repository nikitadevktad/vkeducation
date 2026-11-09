package com.nikitadevktad.vkeducation.data.repository

import org.junit.Assert.assertEquals
import org.junit.Test

class GreetingRepositoryImplTest {
    @Test
    fun returnsExpectedGreetingName() {
        val repository: GreetingRepository = GreetingRepositoryImpl()
        assertEquals("Android", repository.getName())
    }
}
