package com.afkoders.chatoyantview.chatoyant_view

import android.content.Context
import android.graphics.*
import android.hardware.SensorEvent
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.afkoders.chatoyantview.R
import com.afkoders.chatoyantview.toPx
import com.google.android.material.button.MaterialButton
import kotlin.math.abs


/**
 * Created by Kalevych Oleksandr on 30.11.2020.
 */


class ChatoyantButton(context: Context, attrs: AttributeSet) : MaterialButton(context, attrs), ChatoyantStyleable {

    val chatoyantHelper: ChatoyantHelper = object : ChatoyantHelper(context) {
        override fun setupHorizontalShader(event: SensorEvent?) {
            val axisY: Float = event?.values?.get(1) ?: 0f

            if (abs(axisY) > MINIMUM_VALUABLE_GYROSCOPE_CHANGE) {
                if (axisY > 0) previousOffset.value += MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT else previousOffset.value -= MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT

                setupShader()
                this@ChatoyantButton.invalidate()
            }
        }
    }

    var textAboveShine = ""
    init {
        chatoyantHelper.setupGyroscope(context)
        val point = Point()
        context.display?.getRealSize(point)
        parseAttributes(attrs, point)

        textAboveShine = this.text.toString()
        this.text = ""
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
    }

    val paintText = TextPaint().apply {
        color = currentTextColor
        textAlign = Paint.Align.CENTER
        textSize = this@ChatoyantButton.textSize
        gravity = this@ChatoyantButton.gravity
        textAlignment = this@ChatoyantButton.textAlignment
        typeface = this@ChatoyantButton.typeface
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when(visibility){
            VISIBLE -> chatoyantHelper.onResume()
            else -> chatoyantHelper.onPause()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRoundRect(
            0f,
            0f + DEFAULT_INSET_DP.toPx(context),
            this.width.toFloat(),
            height.toFloat() - DEFAULT_INSET_DP.toPx(context),
            cornerRadius.toFloat(),
            cornerRadius.toFloat(),
            chatoyantHelper.paintShader
        )
        val xPos = width / 2
        val yPos = (height / 2 - (paintText.descent() + paintText.ascent()) / 2)

        canvas?.drawText(textAboveShine, xPos.toFloat(), yPos, paintText)
    }


    override fun setAngle(progress: Int) = chatoyantHelper.setAngle(progress)
    override fun setShineWidth(width: Int) = chatoyantHelper.setShineWidthInPercents(width)
    override fun setShineSpeed(speed: Int) = chatoyantHelper.setShineSpeed(speed)
    override fun setRelativeToScreen(relative: Boolean) = chatoyantHelper.setRelativeToScreen(relative)
    override fun setRepeatMode(mode: Int) = chatoyantHelper.setRepeatMode(mode)
    override fun setBitmapForBackground(bitmap: Bitmap?) = chatoyantHelper.setBitmapForBackground(bitmap)

    companion object {
       const val DEFAULT_INSET_DP = 8f
    }
}