package com.pb.news

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pb.common.di.Result
import com.pb.common.models.Article
import com.pb.common.models.Source
import com.pb.news.domain.NewsUseCase
import com.pb.news.presentation.viewmodel.NewsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var newsUseCase: NewsUseCase
    private lateinit var viewModel: NewsViewModel

    companion object {
        val mockArticles = mutableListOf(
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
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        newsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should fetch news articles`() = testScope.runTest {
        // Arrange
        coEvery { newsUseCase.getNews() } returns flowOf(Result.success(mockArticles))

        // Act
        viewModel = NewsViewModel(newsUseCase)

        // Assert
        advanceUntilIdle()
        assertEquals(Result.success(mockArticles), viewModel.newsArticles.value)
    }

    @Test
    fun `fetchNews should update state to loading then success`() = testScope.runTest {
        // Arrange
        coEvery { newsUseCase.getNews() } returns flowOf(
            Result.loading(),
            Result.success(mockArticles)
        )

        viewModel = NewsViewModel(newsUseCase)

        // Act
        viewModel.fetchNews()

        // Assert
        assertEquals(Result.loading<List<Article>>(), viewModel.newsArticles.value)
        advanceUntilIdle()
        assertEquals(Result.success(mockArticles), viewModel.newsArticles.value)
    }

    @Test
    fun `fetchNews should handle error`() = testScope.runTest {
        // Arrange
        val errorMessage = "Failed to fetch news"
        coEvery { newsUseCase.getNews() } returns flowOf(Result.error(errorMessage))

        viewModel = NewsViewModel(newsUseCase)

        // Act
        viewModel.fetchNews()

        // Assert
        advanceUntilIdle()
        assertEquals(Result.error<List<Article>>(errorMessage), viewModel.newsArticles.value)
    }
}
