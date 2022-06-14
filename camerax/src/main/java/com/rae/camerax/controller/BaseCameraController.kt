package com.rae.camerax.controller

import android.util.Log

/**
 * 最基本的控制器，提供拍照、录像、摄像头切换功能
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
class BaseCameraController : CameraController() {

    override fun onViewCreated() {
        super.onViewCreated()

        Log.d(TAG, "BaseCameraController onViewCreated")
    }

    override fun onCreated() {
        Log.d(TAG, "BaseCameraController onCreated")
    }
}