package com.nikitadevktad.vkeducation.data.mapper

import com.nikitadevktad.vkeducation.data.dto.CatalogAppDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplicationMapperTest {
    @Test
    fun `maps catalog dto to domain model`() {
        val source = CatalogAppDto(
            id = "7",
            name = "Test",
            description = "Test description",
            category = "Education",
            iconUrl = "https://example.com/icon.png",
        )

        val result = source.toDomain()

        assertEquals("7", result.id)
        assertEquals("Test", result.name)
    }
}
