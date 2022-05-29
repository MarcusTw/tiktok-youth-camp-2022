package com.example.ttyc

import junit.framework.TestCase.assertNotNull
import org.junit.Test

class MovieApiServiceTest {
    @Test
    fun isApiKeyPresent() {
        val apiKey = BuildConfig.ApiKey
        assertNotNull(apiKey)
        assert(apiKey != "")
    }
}