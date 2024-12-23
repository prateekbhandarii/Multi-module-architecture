package com.pb.user.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.pb.common.ui.CustomAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(drawerState: DrawerState, navController: NavHostController) {
    var showModalBottomSheet by remember { mutableStateOf(false) }
    var currentStep by remember { mutableIntStateOf(1) }

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    Scaffold(
        topBar = { CustomAppBar(drawerState = drawerState, title = "User Info") }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { showModalBottomSheet = true }) {
                Text("Click Me!")
            }

            if (showModalBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(),
                    onDismissRequest = { showModalBottomSheet = false },
                    sheetState = bottomSheetState
                ) {
                    when (currentStep) {
                        1 -> StepOneContent(
                            onNextClicked = { currentStep = 2 },
                            onCloseClicked = { showModalBottomSheet = false }
                        )

                        2 -> StepTwoContent(
                            onPreviousClicked = { currentStep = 1 },
                            onCloseClicked = { showModalBottomSheet = false }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StepOneContent(onNextClicked: () -> Unit, onCloseClicked: () -> Unit) {
    StepContent(
        title = "Step 1",
        description = "This is the first step in the bottom sheet.",
        onPreviousClicked = null,
        onNextClicked = onNextClicked,
        imageUrl = "https://i0.wp.com/picjumbo.com/wp-content/uploads/gorgeous-sunset-over-the-sea-free-image.jpeg?h=800&quality=80",
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun StepTwoContent(onPreviousClicked: () -> Unit, onCloseClicked: () -> Unit) {
    StepContent(
        title = "Step 2",
        description = "This is the second step in the bottom sheet.",
        onPreviousClicked = onPreviousClicked,
        onNextClicked = null,
        imageUrl = "https://i0.wp.com/picjumbo.com/wp-content/uploads/silhouette-of-a-guy-with-a-cap-at-red-sky-sunset-free-image.jpeg?h=800&quality=80",
        onCloseClicked = onCloseClicked
    )
}

@Composable
fun StepContent(
    title: String,
    description: String,
    imageUrl: String,
    onPreviousClicked: (() -> Unit)? = null,
    onNextClicked: (() -> Unit)? = null,
    onCloseClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(description)
        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f), // Image takes 80% of the height
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            onPreviousClicked?.let {
                Button(onClick = it) {
                    Text("Previous")
                }
            }
            onCloseClicked.let {
                Button(onClick = it) {
                    Text("Close")
                }
            }
            onNextClicked?.let {
                Button(onClick = it) {
                    Text("Next")
                }
            }
        }
    }
}
