package com.example.trl.camera

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraPreview(
    modifier: Modifier = Modifier,
    onTextDetected: (String) -> Unit
) {

    val lifecycleOwner =
        LocalLifecycleOwner.current

    AndroidView(

        modifier = modifier,

        factory = { context ->

            val previewView =
                PreviewView(context)

            val cameraProviderFuture =
                ProcessCameraProvider
                    .getInstance(context)

            cameraProviderFuture
                .addListener({

                    val cameraProvider =
                        cameraProviderFuture.get()

                    val preview =
                        Preview.Builder()
                            .build()

                    val imageAnalysis =
                        ImageAnalysis.Builder()

                            .setBackpressureStrategy(
                                ImageAnalysis
                                    .STRATEGY_KEEP_ONLY_LATEST
                            )

                            .build()

                    preview.surfaceProvider =
                        previewView.surfaceProvider

                    imageAnalysis.setAnalyzer(

                        ContextCompat
                            .getMainExecutor(
                                context
                            ),

                        com.example.trl.ocr
                            .OCRAnalyzer(
                                onTextDetected
                            )
                    )

                    val selector =
                        CameraSelector
                            .DEFAULT_BACK_CAMERA

                    try {

                        cameraProvider
                            .unbindAll()

                        val camera =
                            cameraProvider
                                .bindToLifecycle(

                                    lifecycleOwner,

                                    selector,

                                    preview,

                                    imageAnalysis
                                )

                        CameraController
                            .camera = camera

                    } catch (e: Exception) {

                        e.printStackTrace()
                    }

                },
                    ContextCompat
                        .getMainExecutor(
                            context
                        )
                )

            previewView
        }
    )
}