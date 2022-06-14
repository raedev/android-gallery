package com.rae.camerax.view

import android.content.Context
import android.widget.FrameLayout
import java.lang.ref.WeakReference

/**
 * 相机控制层
 * @author RAE
 * @date 2022/06/14
 * @copyright Copyright (c) https://github.com/raedev All rights reserved.
 */
abstract class CameraControlView(context: Context) :
    FrameLayout(context) {

    private lateinit var viewWeakReference: WeakReference<CameraRenderView>
    var renderView: CameraRenderView?
        set(value) {
            this.viewWeakReference = WeakReference(value)
        }
        get() {
            return this.viewWeakReference.get()
        }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        viewWeakReference.clear()
    }

}