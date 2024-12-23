package com.pb.user.nav

import androidx.compose.material3.DrawerState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.pb.user.screens.UserScreen

const val userRoute = "user"

fun NavGraphBuilder.userScreen(
    drawerState: DrawerState,
    navController: NavHostController,
) {
    composable(userRoute) {
        UserScreen(
            drawerState = drawerState,
            navController = navController
        )
    }

}