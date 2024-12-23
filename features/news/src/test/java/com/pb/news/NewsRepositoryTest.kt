package com.pb.news.data.repository

import com.pb.common.di.Status
import com.pb.common.models.Article
import com.pb.common.models.Source
import com.pb.news.data.source.NewsRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NewsRepositoryTest {

    private lateinit var newsRepository: NewsRepository
    private val remoteDataSource = mockk<NewsRemoteDataSource>()
    private val mockArticles = mutableListOf(
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

    @Before
    fun setUp() {
        newsRepository = NewsRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `getNews returns articles successfully`() = runTest {
        // Arrange
        coEvery { remoteDataSource.getNews() } returns mockArticles

        // Act
        val flow = newsRepository.getNews().toList()

        // Assert
        assertTrue(flow.first().status == Status.LOADING)
        assertTrue(flow.last().status == Status.SUCCESS)
        assertEquals(mockArticles, flow.last().data)
        coVerify(exactly = 1) { remoteDataSource.getNews() }
    }

    @Test
    fun `getNews emits error when exception occurs`() = runTest {
        // Arrange
        val exception = RuntimeException("Failed to fetch news")
        coEvery { remoteDataSource.getNews() } throws exception

        // Act
        val flow = newsRepository.getNews().toList()

        // Assert
        assertTrue(flow.first().status == Status.LOADING)
        assertTrue(flow.last().status == Status.ERROR)
        assertEquals("Failed to fetch news", flow.last().message)
        coVerify(exactly = 1) { remoteDataSource.getNews() }
    }
}
