package com.rae.camerax.controller

import com.rae.camerax.view.CameraControlView

/**
 * 相机控制器
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class CameraController {
    companion object {
        const val TAG = "CameraController"
    }

    /**
     * 控制器关联View，如果关联View，将自动加进去相机容器中去
     */
    open var controlView: CameraControlView? = null
        protected set


    /**
     * 当[controlView]不为空时并且已经加载到相机容器的时候触发回调
     */
    open fun onViewCreated() {
        // 空方法，由子类实现
    }

    /**
     * 当前控制器被加载到相机容器中去的时候回调
     */
    abstract fun onCreated()
}