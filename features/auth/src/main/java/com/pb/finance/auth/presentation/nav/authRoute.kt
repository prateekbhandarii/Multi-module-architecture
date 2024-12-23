package com.pb.finance.auth.presentation.nav

import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pb.finance.auth.presentation.screens.AuthScreen
import com.pb.finance.auth.presentation.viewmodel.AuthNavigationViewModel

const val authRoute = "auth"

fun NavGraphBuilder.authScreen(
    drawerState: DrawerState,
    navController: NavHostController,
    authViewModel: AuthNavigationViewModel,
) {
    composable(authRoute) {
        AuthScreen(
            drawerState = drawerState,
            navController = navController,
            authViewModel = authViewModel
        )
    }
}