package com.groot.facerecognition.ui.screen

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.groot.facerecognition.R
import com.groot.facerecognition.navigation.Route
import com.groot.facerecognition.ui.composable.NavBarItem
import com.groot.facerecognition.ui.theme.Purple80

@Composable
fun HomeScreen() {
    val context = LocalContext.current


    val defaultBottomBarItems: List<NavBarItem> = listOf(
        NavBarItem(Route.FACE_RECOGNITION, R.drawable.ic_home),
        NavBarItem(Route.ADD_FACE, R.drawable.ic_add_face),
        NavBarItem(Route.FACE_LIST, R.drawable.ic_faces),
//        NavBarItem(Routes.Settings, R.drawable.ic_settings),
    )

    val currentItem = remember { mutableStateOf<NavBarItem>(defaultBottomBarItems.first()) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButtonPosition = FabPosition.End,
        bottomBar = {
            NavigationBar(Modifier.height(65.dp)) {
                defaultBottomBarItems.forEach { item ->
                    NavigationBarItem(
                        selected = currentItem.value == item,
                        onClick = {
                            currentItem.value = item
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                        ),
                        label = { Text(text = item.text) },
                        icon = { Icon(painter = painterResource(item.icon), contentDescription = item.route) },
                        interactionSource = remember { MutableInteractionSource() }
                    )
                }
            }
        },
        snackbarHost = {
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = contentColorFor(MaterialTheme.colorScheme.background),
        content = { padding ->
            val modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding)

            when (currentItem.value.route) {
                Route.ADD_FACE -> AddFacesView(modifier)
                Route.FACE_RECOGNITION -> FaceRecognitionView(modifier)
                Route.FACE_LIST -> FaceRecognitionView(modifier)
            }

        }
    )
}