package com.pb.multi_module_example

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pb.home.nav.homeRoute
import com.pb.home.nav.homeScreen

@Composable
fun MainNavigation(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(navController = navController, startDestination = homeRoute) {
        homeScreen {
            navController.navigateUp()
        }
    }
}