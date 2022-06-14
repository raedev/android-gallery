package com.rae.camerax.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.lifecycle.LifecycleOwner
import com.rae.camerax.controller.BaseCameraController
import com.rae.camerax.controller.CameraController

/**
 * 相机装载容器，装载相机渲染层和控制层。
 * 渲染层加载完毕后会传递给控制层进行自定义的功能处理。
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class CameraContainer(context: Context, attr: AttributeSet?) :
    FrameLayout(context, attr) {
    /**
     * 相机状态回调
     */
    interface CameraStateListener {

        /**
         * 相机发生错误时回调
         *
         * @param stateCode 状态码
         * @param message 错误消息
         */
        fun onCameraError(stateCode: Int, message: String)
    }

    private val renderView = CameraRenderView(context)
    private val controllers = mutableListOf<CameraController>()

    init {
        addView(renderView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))

    }

    /** 绑定生命周期 */
    fun bindLifecycleOwner(lifecycleOwner: LifecycleOwner) {
        // 初始化
        renderView.setup(lifecycleOwner)
        addController(BaseCameraController())
    }

    fun addController(controller: CameraController) {
        // 禁止重复添加
        if (controllers.contains(controller)) return
        controller.controlView?.let {
            addView(
                it,
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            )
            // 附加渲染层
            it.renderView = this.renderView
            it.post {
                controller.onViewCreated()
            }
        }
        controllers.add(controller)
        controller.onCreated()
    }

    fun removeController(controller: CameraController) {
        controllers.contains(controller).let {
            // 移除View
            if (controller.controlView != null) removeView(controller.controlView)
            // 移除控制器
            controllers.remove(controller)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 移除控制器
        controllers.forEach { removeController(it) }
        controllers.clear()
    }


}