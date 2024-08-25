package com.waqas.tracker_data.repository

import com.google.common.truth.Truth.assertThat
import com.waqas.tracker_data.remote.OpenFoodApi
import com.waqas.tracker_data.remote.malformedFoodResponse
import com.waqas.tracker_data.remote.validFoodResponse
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.time.Duration

class TrackerRepositoryImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var repository: TrackerRepositoryImpl
    private lateinit var api: OpenFoodApi
    private lateinit var okHttpClient: OkHttpClient


    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        okHttpClient = OkHttpClient.Builder()
            .callTimeout(Duration.ofSeconds(1))
            .readTimeout(Duration.ofSeconds(1))
            .writeTimeout(Duration.ofSeconds(1))
            .build()
        api = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .baseUrl(mockWebServer.url("/"))
            .build()
            .create(OpenFoodApi::class.java)

        repository = TrackerRepositoryImpl(
            dao = mockk(relaxed = true),
            api = api
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `Search food, valid response, returns results`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(validFoodResponse))
        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isSuccess).isTrue()
    }


    @Test
    fun `Search food, invalid response, returns failure`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(403).setBody(validFoodResponse))
        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue()
    }


    @Test
    fun `Search food, malformed response, returns failure`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setBody(malformedFoodResponse))
        val result = repository.searchFood("banana", 1, 40)
        assertThat(result.isFailure).isTrue()
    }


}