package com.groot.facerecognition.ui.screen

import androidx.camera.core.ImageCapture
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.groot.facerecognition.ui.camera.CameraPreview


@Composable
fun FaceRecognitionView(modifier: Modifier) {

    val context = LocalContext.current

    val imageCapture: MutableState<ImageCapture?> = remember { mutableStateOf(null) }
    val executor = remember(context) { ContextCompat.getMainExecutor(context) }

    CameraPreview(modifier, imageCapture, executor, false) {}

}