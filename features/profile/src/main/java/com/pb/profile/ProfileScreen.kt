package com.pb.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pb.common.ui.CustomAppBar


@Composable
fun ProfileScreen(drawerState: DrawerState, navController: NavHostController) {
    val viewModel: ProfileViewModel = hiltViewModel()
    val userInfo by viewModel.userInfo.collectAsState()

    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "Profile") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            userInfo?.let { user ->
                Text("ID: ${user.id}")
                Text("Name: ${user.name}")
                Text("Email: ${user.email}")
            } ?: CircularProgressIndicator()
        }
    }
}
