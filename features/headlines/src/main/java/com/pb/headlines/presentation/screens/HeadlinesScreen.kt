package com.pb.headlines.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pb.common.di.Status
import com.pb.common.ui.CustomAppBar
import com.pb.common.ui.NewsItem
import com.pb.headlines.presentation.viewmodel.HeadlinesViewModel
import com.pb.newsdetails.nav.navigateToNewsDetails
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HeadlinesScreen(
    drawerState: DrawerState,
    navController: NavHostController,
) {
    val viewModel: HeadlinesViewModel = hiltViewModel()

    val newsState by viewModel.headlinesNews.collectAsState()

    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = "Headlines"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (newsState.status) {
                Status.LOADING -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                Status.SUCCESS -> {
                    val news = newsState.data

                    LazyColumn {
                        news?.let {
                            items(
                                count = it.size,
                                contentType = { index -> "news" }
                            ) { index ->
                                val article = it[index]
                                NewsItem(article = article) {
                                    val encodedUrl = URLEncoder.encode(
                                        article.url,
                                        StandardCharsets.UTF_8.toString()
                                    )
                                    navController.navigateToNewsDetails(encodedUrl)
                                }
                            }
                        }
                    }
                }

                Status.ERROR -> {
                    Text(
                        text = "Error: ${newsState.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}