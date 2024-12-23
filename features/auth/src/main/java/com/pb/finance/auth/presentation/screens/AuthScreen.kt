package com.pb.finance.auth.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import com.pb.finance.auth.presentation.viewmodel.AuthNavigationViewModel
import com.pb.common.models.GreenIdAuthState
import com.pb.common.ui.CustomAppBar

@Composable
fun AuthScreen(
    navController: NavHostController,
    drawerState: DrawerState,
    authViewModel: AuthNavigationViewModel
) {
    val state = authViewModel.uiState.value
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CustomAppBar(
                drawerState = drawerState,
                title = "GreenId Navigation"
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
            // Your screen content here, such as buttons or text
            Text("Welcome to the GreenId Authentication Screen!")
            when (state) {
                is GreenIdAuthState.Idle -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Welcome to Booster App!")
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { authViewModel.initiateAuth() }) {
                            Text("Start Authentication")
                        }
                    }
                }

                is GreenIdAuthState.RedirectToBrowser -> {
                    LaunchedEffect(state.url) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.url))
                        ContextCompat.startActivity(context, intent, null)
                    }
                }

                is GreenIdAuthState.Authenticated -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Authentication successful!")
                    }
                }

                is GreenIdAuthState.Canceled -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(state.message)
                        Button(onClick = { authViewModel.initiateAuth() }) {
                            Text("Retry Authentication")
                        }
                    }
                }

                is GreenIdAuthState.Failure -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Authentication failed: ${state.error}")
                        Button(onClick = { authViewModel.initiateAuth() }) {
                            Text("Retry Authentication")
                        }
                    }
                }
            }
        }
    }
}
