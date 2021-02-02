package com.afkoders.chatoyantview.chatoyant_view

/**
 * Created by Kalevych Oleksandr on 12.01.2021.
 */

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.hardware.SensorEvent
import android.util.AttributeSet
import android.view.View
import com.afkoders.chatoyantview.R
import com.google.android.material.textview.MaterialTextView
import kotlin.math.abs

class ChatoyantTextView(context: Context, attrs: AttributeSet) : MaterialTextView(context, attrs), ChatoyantStyleable {

    val chatoyantHelper: ChatoyantHelper = object : ChatoyantHelper(context) {
        override fun setupHorizontalShader(event: SensorEvent?) {
            val axisY: Float = event?.values?.get(1) ?: 0f

            if (abs(axisY) > MINIMUM_VALUABLE_GYROSCOPE_CHANGE) {
                if (axisY > 0) previousOffset.value += MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT else previousOffset.value -= MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT

                setupShaderWithTextColor(previousOffset.value, this@ChatoyantTextView.currentTextColor)
                paint.shader = paintShader.shader
                this@ChatoyantTextView.invalidate()
            }
        }
    }

    // Register the listener
    init {
        chatoyantHelper.setupGyroscope(context)
        val point = Point()
        context.display?.getRealSize(point)
        parseAttributes(attrs, point)

    }

    private fun parseAttributes(attributeSet: AttributeSet?, point: Point) {
        if (attributeSet == null) return
        with(context.obtainStyledAttributes(attributeSet, R.styleable.ChatoyantView)) {
            chatoyantHelper.parseAttributes(this, point)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        chatoyantHelper.layout(width)
        chatoyantHelper.setupShaderWithTextColor(textColor = this.currentTextColor)
        paint.shader = chatoyantHelper.paintShader.shader
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (visibility) {
            VISIBLE -> chatoyantHelper.onResume()
            else -> chatoyantHelper.onPause()
        }
    }

    override fun setAngle(progress: Int) = chatoyantHelper.setAngle(progress)
    override fun setShineWidth(width: Int) = chatoyantHelper.setShineWidthInPercents(width)
    override fun setShineSpeed(speed: Int) = chatoyantHelper.setShineSpeed(speed)
    override fun setRelativeToScreen(relative: Boolean) = chatoyantHelper.setRelativeToScreen(relative)
    override fun setRepeatMode(mode: Int) = chatoyantHelper.setRepeatMode(mode)
    override fun setBitmapForBackground(bitmap: Bitmap?) = chatoyantHelper.setBitmapForBackground(bitmap)

}