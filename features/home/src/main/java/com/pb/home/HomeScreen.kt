package com.pb.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.pb.finance.auth.presentation.nav.authRoute
import com.pb.finance.auth.presentation.nav.authScreen
import com.pb.finance.auth.presentation.viewmodel.AuthNavigationViewModel
import com.pb.common.models.GreenIdAuthState
import com.pb.headlines.presentation.nav.headLinesRoute
import com.pb.headlines.presentation.nav.headLinesScreen
import com.pb.home.nav.homeRoute
import com.pb.news.presentation.nav.newsRoute
import com.pb.news.presentation.nav.newsScreen
import com.pb.profile.nav.profileRoute
import com.pb.profile.nav.profileScreen
import com.pb.user.nav.userRoute
import com.pb.user.nav.userScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private data class DrawerMenu(val icon: ImageVector, val title: String, val route: String)

private val menus = arrayOf(
    DrawerMenu(Icons.Filled.Home, "News", newsRoute),
    DrawerMenu(Icons.Default.Create, "Headlines", headLinesRoute),
    DrawerMenu(Icons.Default.Face, "User", userRoute),
    DrawerMenu(Icons.Default.AccountBox, "Profile", profileRoute),
    DrawerMenu(Icons.Default.Build, "Auth", authRoute),
)

@Composable
private fun DrawerContent(
    menus: Array<DrawerMenu>,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(150.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                }
            )
        }
    }
}

@Composable
internal fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val authViewModel: AuthNavigationViewModel = hiltViewModel()
    val authState by authViewModel.authState.collectAsState()

    LaunchedEffect(authState) {
        when (authState) {
            is GreenIdAuthState.Authenticated, is GreenIdAuthState.Failure -> {
                authViewModel.updateState(authState)
                navController.navigate(authRoute) {
                    popUpTo(homeRoute) { inclusive = true }
                }
            }

            else -> Unit // No action needed
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(menus) { route ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    navController.navigate(route)
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = newsRoute) {

            newsScreen(drawerState, navController)
            headLinesScreen(drawerState, navController)
            userScreen(drawerState, navController)
            profileScreen(drawerState, navController)
            authScreen(drawerState, navController, authViewModel)
        }
    }
}


