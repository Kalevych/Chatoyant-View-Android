package com.afkoders.chatoyantview.chatoyant_view

import android.graphics.Bitmap

/**
 * Created by Kalevych Oleksandr on 01.02.2021.
 */
interface ChatoyantStyleable {
    fun setAngle(progress: Int)
    fun setShineWidth(width: Int)
    fun setShineSpeed(speed: Int)
    fun setRelativeToScreen(relative: Boolean)
    fun setRepeatMode(mode: Int)
    fun setBitmapForBackground(bitmap: Bitmap?)
}