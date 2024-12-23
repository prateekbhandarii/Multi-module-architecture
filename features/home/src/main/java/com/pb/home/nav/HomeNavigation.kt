package com.pb.home.nav

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.pb.home.HomeScreen

const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(function: () -> Boolean) {
    composable(homeRoute) {
        HomeScreen()
    }
}