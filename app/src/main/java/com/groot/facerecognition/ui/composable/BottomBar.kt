package com.groot.facerecognition.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.Keep


@Keep
data class NavBarItem(
    val route: String,
    @DrawableRes val icon: Int,
    val text: String = route,
)