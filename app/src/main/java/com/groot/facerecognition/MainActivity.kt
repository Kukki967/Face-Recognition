package com.groot.facerecognition

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import com.groot.facerecognition.ui.screen.HomeScreen
import com.groot.facerecognition.ui.theme.FaceRecognitionTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    val cameraPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Implement camera related  code
        } else {
            // Camera permission denied
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FaceRecognitionTheme {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) -> {
                        HomeScreen()
                    }

                    else -> {
                        cameraPermissionRequest.launch(android.Manifest.permission.CAMERA)
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FaceRecognitionTheme {
    }
}