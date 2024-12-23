package com.pb.news

import com.pb.common.di.Result
import com.pb.common.models.Article
import com.pb.common.models.Source
import com.pb.news.data.repository.NewsRepository
import com.pb.news.domain.NewsUseCaseImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NewsUseCaseTest {

    private lateinit var newsUseCase: NewsUseCaseImpl
    private val repository = mockk<NewsRepository>()

    @Before
    fun setUp() {
        newsUseCase = NewsUseCaseImpl(repository)
    }

    @Test
    fun `getNews calls repository and returns success result when news articles are loaded`() =
        runTest {
            // Arrange
            val articles = mutableListOf(
                Article(
                    author = "Nikki Birrell",
                    content = "Paella content...",
                    description = "Discover the steps to creating a flavour-packed paella.",
                    publishedAt = "2024-11-08T23:00:00Z",
                    source = Source(id = "1", name = "New Zealand Herald"),
                    title = "How to make the perfect paella",
                    url = "https://example.com",
                    urlToImage = "https://example.com/image.jpg"
                )
            )
            val expectedResult = Result.success(articles)

            coEvery { repository.getNews() } returns flow { emit(expectedResult) }

            // Act
            val result = newsUseCase.getNews()

            // Assert
            result.collect { res ->
                assertEquals(expectedResult, res)
            }
            coVerify(exactly = 1) { repository.getNews() }
        }

    @Test
    fun `getNews calls repository and returns error result when an exception occurs`() = runTest {
        // Arrange
        val errorMessage = "Failed to fetch news"
        val expectedResult = Result.error<MutableList<Article>>(errorMessage, null)

        coEvery { repository.getNews() } returns flow { emit(expectedResult) }

        // Act
        val result = newsUseCase.getNews()

        // Assert
        result.collect { res ->
            assertEquals(expectedResult, res)
        }
        coVerify(exactly = 1) { repository.getNews() }
    }
}
