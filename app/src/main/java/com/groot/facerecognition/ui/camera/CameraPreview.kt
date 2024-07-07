package com.groot.facerecognition.ui.camera


import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.provider.MediaStore
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.groot.facerecognition.R
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@Composable
fun CameraPreview(
    modifier: Modifier,
    imageCapture: MutableState<ImageCapture?>,
    executor: Executor,
    showBottomButton: Boolean,
    captureImageFunction: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val previewCameraView = remember { PreviewView(context) }
    val cameraProviderFuture = remember(context) { ProcessCameraProvider.getInstance(context) }
    val cameraProvider = remember(cameraProviderFuture) { cameraProviderFuture.get() }

    val lensFacing = remember { mutableStateOf(CameraSelector.LENS_FACING_FRONT) }


    Box(modifier, contentAlignment = Alignment.BottomCenter) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {
                cameraProviderFuture.addListener(
                    {
                        val cameraSelector = CameraSelector.Builder()
                            .requireLensFacing(lensFacing.value)
                            .build()

                        imageCapture.value = ImageCapture.Builder().build()

                        cameraProvider.unbindAll()

                        val prev = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewCameraView.surfaceProvider)
                        }

                        cameraProvider.bindToLifecycle(lifecycleOwner, cameraSelector, imageCapture.value, prev)
                    }, executor
                )
                previewCameraView
            }
        )

        if (showBottomButton) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.White),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_flip_camera),
                    contentDescription = "flip camera",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            if (lensFacing.value == CameraSelector.LENS_FACING_FRONT) {
                                lensFacing.value = CameraSelector.LENS_FACING_BACK
                            } else {
                                lensFacing.value = CameraSelector.LENS_FACING_FRONT
                            }
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_launcher),
                    contentDescription = "capture photo",
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            captureImage(imageCapture.value!!, context) {
                                captureImageFunction()
                            }
                        }
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_settings),
                    contentDescription = "settings",
                    modifier = Modifier
                        .size(25.dp)
                        .clickable { }
                )


            }
        }
    }

}

private suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        ProcessCameraProvider.getInstance(this).also { cameraProvider ->
            cameraProvider.addListener({
                continuation.resume(cameraProvider.get())
            }, ContextCompat.getMainExecutor(this))
        }
    }


private fun captureImage(imageCapture: ImageCapture, context: Context, function: () -> Unit) {
    val name = "CameraxImage.jpeg"

    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, name)
        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
        }
    }

    val outputOptions = ImageCapture.OutputFileOptions
        .Builder(
            context.contentResolver,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            contentValues
        )
        .build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                println("image captured successfully and stored at ${outputFileResults.savedUri?.path}")
            }

            override fun onError(exception: ImageCaptureException) {
                println("Failed $exception")
            }

        })
}