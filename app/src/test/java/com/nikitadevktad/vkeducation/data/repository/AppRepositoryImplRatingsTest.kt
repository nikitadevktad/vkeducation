package com.nikitadevktad.vkeducation.data.repository

import com.nikitadevktad.vkeducation.data.mapper.AppMapper
import com.nikitadevktad.vkeducation.data.source.AppApi
import com.nikitadevktad.vkeducation.data.source.AppLocalDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AppRepositoryImplRatingsTest {
    @Test
    fun `sequential loading returns ratings in identifier order`() = runTest {
        val api = RecordingApi(mapOf("one" to 3.1f, "two" to 4.2f))
        val repository = repository(api)

        assertEquals(mapOf("one" to 3.1f, "two" to 4.2f), repository.loadRatingsSequentially(listOf("one", "two")))
        assertEquals(listOf("one", "two"), api.calls)
        assertEquals(1, api.maxConcurrentRequests)
    }

    @Test
    fun `parallel loading starts requests concurrently and keeps identifiers`() = runTest {
        val api = RecordingApi(mapOf("one" to 3.1f, "two" to 4.2f), delayMillis = 100)
        val repository = repository(api)

        assertEquals(mapOf("one" to 3.1f, "two" to 4.2f), repository.loadRatingsInParallel(listOf("one", "two")))
        assertEquals(2, api.maxConcurrentRequests)
    }

    @Test
    fun `empty identifiers return empty maps`() = runTest {
        val repository = repository(RecordingApi(emptyMap()))

        assertTrue(repository.loadRatingsSequentially(emptyList()).isEmpty())
        assertTrue(repository.loadRatingsInParallel(emptyList()).isEmpty())
    }

    @Test
    fun `parallel child failure is propagated`() = runTest {
        val repository = repository(object : AppApi {
            override suspend fun getRating(id: String): Float = error("network error")
        })

        try {
            repository.loadRatingsInParallel(listOf("one"))
        } catch (error: IllegalStateException) {
            assertEquals("network error", error.message)
            return@runTest
        }
        error("Expected child failure")
    }

    @Test
    fun `cancelling parent cancels parallel children`() = runTest {
        var cancelledChildren = 0
        val repository = repository(object : AppApi {
            override suspend fun getRating(id: String): Float = try {
                awaitCancellation()
            } finally {
                cancelledChildren++
            }
        })

        val job = launch { repository.loadRatingsInParallel(listOf("one", "two")) }
        testScheduler.advanceUntilIdle()
        job.cancelAndJoin()

        assertEquals(2, cancelledChildren)
    }

    private fun repository(api: AppApi) = AppRepositoryImpl(AppLocalDataSource(), AppMapper(), api)

    private class RecordingApi(
        private val ratings: Map<String, Float>,
        private val delayMillis: Long = 0,
    ) : AppApi {
        val calls = mutableListOf<String>()
        var maxConcurrentRequests = 0
        private var activeRequests = 0

        override suspend fun getRating(id: String): Float {
            calls += id
            activeRequests++
            maxConcurrentRequests = maxOf(maxConcurrentRequests, activeRequests)
            try {
                delay(delayMillis)
                return ratings.getValue(id)
            } finally {
                activeRequests--
            }
        }
    }
}
