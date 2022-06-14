package com.rae.camerax.view

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.rae.camerax.R
import kotlin.math.abs

/**
 * 相机渲染层
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CameraRenderView(context: Context) :
    FrameLayout(context) {

    companion object {
        const val TAG = "CameraController"

        /** 4:3 模式 */
        const val RATIO_4_3_VALUE: Double = 4.0 / 3.0

        /** 16:9 模式 */
        const val RATIO_16_9_VALUE: Double = 16.0 / 9.0
    }


    // 相机预览View
    private val previewView: PreviewView = PreviewView(context)

    /** 相机提供者 */
    private var cameraProvider: ProcessCameraProvider? = null

    /** 前置/后置摄像头 */
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    /** 相机 */
    private var camera: Camera? = null

    private var lifecycleOwner: LifecycleOwner? = null

    /** WindowManager */
    private val windowManager: WindowManager by lazy { context.getSystemService(Context.WINDOW_SERVICE) as WindowManager }

    init {
        this.addView(
            previewView,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
    }

    /** 初始化相机 */
    internal fun setup(lifecycleOwner: LifecycleOwner) {
        this.lifecycleOwner = lifecycleOwner
        val provider = ProcessCameraProvider.getInstance(context)
        provider.addListener({
            // 得到提供的摄像头
            this.cameraProvider = provider.get()
            // 当前镜头
            this.lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
                else -> {
                    this.onCameraError(
                        CameraState.ERROR_CAMERA_DISABLED,
                        context.getString(R.string.camerax_error_not_fond)
                    )
                    return@addListener
                }
            }
            previewView.post {
                bindCameraUseCases()
            }
        }, ContextCompat.getMainExecutor(context))
    }

    /** 绑定相机用例 */
    internal fun bindCameraUseCases() {
        if (cameraProvider == null) {
            onCameraError(
                CameraState.ERROR_CAMERA_FATAL_ERROR,
                context.getString(R.string.camerax_error_not_fond)
            )
            return
        }
        // 摄像机展示的大小
        val metrics: Rect = when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> windowManager.currentWindowMetrics.bounds
            else -> Rect(
                0,
                0,
                windowManager.defaultDisplay.width,
                windowManager.defaultDisplay.height
            )
        }

        val rotation = previewView.display.rotation
        val screenAspectRatio = aspectRatio(metrics.width(), metrics.height())
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
        Log.d(TAG, "屏幕大小: ${metrics.width()} x ${metrics.height()}，屏幕比例： $screenAspectRatio")
        // Preview
        val preview = Preview.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation
            .setTargetRotation(rotation)
            .build()

        // 在重新绑定之前，必须先解绑
        cameraProvider?.unbindAll()
        lifecycleOwner?.let {
            camera = this.cameraProvider?.bindToLifecycle(it, cameraSelector, preview)
        }
        preview.setSurfaceProvider(previewView.surfaceProvider)
    }

    /** 判断是否有后置摄像头 */
    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    /** 判断是否有前置摄像头 */
    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    /** 计算屏幕比例 */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = width.coerceAtLeast(height) * 0.1 / (width.coerceAtMost(height) * 0.1)
        return when {
            abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE) -> AspectRatio.RATIO_4_3
            else -> AspectRatio.RATIO_16_9
        }
    }

    /** 相机发生错误回调 */
    private fun onCameraError(stateCode: Int, message: String) {
        Log.e(TAG, "Camera Error: $stateCode: $message")
    }

}