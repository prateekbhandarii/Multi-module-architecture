package com.pb.news

import com.pb.common.exception.NoDataException
import com.pb.common.models.NewsResponse
import com.pb.news.data.service.NewsService
import com.pb.news.data.source.NewsRemoteDataSourceImpl
import com.google.gson.Gson
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.InputStreamReader

class NewsRemoteDataSourceTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var service: NewsService
    private lateinit var newsRepository: NewsRemoteDataSourceImpl
    private val gson = Gson()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        service = mockk()
        newsRepository = NewsRemoteDataSourceImpl(service, testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    // Helper function to load JSON from resources
    private fun loadJson(filename: String): String {
        val inputStream = javaClass.classLoader!!.getResourceAsStream("raw/$filename")
            ?: throw IllegalArgumentException("File not found: $filename")
        return InputStreamReader(inputStream).use { it.readText() }
    }

    // Helper function to create a Retrofit Response
    private fun <T> createResponse(body: T?, isSuccessful: Boolean = true): Response<T> {
        return if (isSuccessful) {
            Response.success(body)
        } else {
            Response.error(400, "Error".toResponseBody("application/json".toMediaTypeOrNull()))
        }
    }

    @Test
    fun `getNews returns list of articles on successful response`() = runTest {
        // Arrange
        val newsJson = loadJson("news.json")
        val newsResponse = gson.fromJson(newsJson, NewsResponse::class.java)
        coEvery { service.getNews() } returns CompletableDeferred(createResponse(newsResponse))

        // Act
        val result = newsRepository.getNews()

        // Assert
        assertEquals(newsResponse.articles, result)
        coVerify(exactly = 1) { service.getNews() }
    }

    @Test
    fun `getNews throws HttpException on HTTP error response`() = runTest {
        // Arrange
        val errorResponse = CompletableDeferred(
            Response.error<NewsResponse>(404, "Not Found".toResponseBody(null))
        )
        coEvery { service.getNews() } returns errorResponse

        // Act & Assert
        val exception = assertThrows<HttpException> {
            newsRepository.getNews()
        }
        assertEquals(404, exception.code())
        coVerify(exactly = 1) { service.getNews() }
    }

    @Test
    fun `getNews throws NoDataException when response body is null`() = runTest {
        // Arrange
        val response = Response.success<NewsResponse>(null)
        val deferred = CompletableDeferred(response)
        coEvery { service.getNews() } returns deferred

        // Act & Assert
        val exception = assertThrows<NoDataException> {
            newsRepository.getNews()
        }
        assertEquals("Response body is null", exception.message)
        coVerify(exactly = 1) { service.getNews() }
    }

    @Test
    fun `getNews throws IOException on network failure`() = runTest {
        // Arrange
        coEvery { service.getNews() } throws IOException("Network Failure")

        // Act & Assert
        val exception = assertThrows<IOException> {
            newsRepository.getNews()
        }
        assertEquals("Network error occurred: Network Failure", exception.message)
        coVerify(exactly = 1) { service.getNews() }
    }
}
