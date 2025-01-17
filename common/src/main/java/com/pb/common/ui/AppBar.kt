package com.pb.common.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.pb.common.theme.AppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomAppBar(drawerState: DrawerState? = null, title: String) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        navigationIcon = {
            if (drawerState != null) {
                IconButton(onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }) {
                    Icon(Icons.Filled.Menu, contentDescription = "")
                }
            }
        },
        title = { Text(text = title) }
    )
}


@Preview(widthDp = 300)
@Composable
fun PreviewCustomAppBar() {
    AppTheme {
        CustomAppBar(drawerState = null, title = "Title")
    }

}