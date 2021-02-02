package com.afkoders.chatoyantview.chatoyant_view

import android.content.Context
import android.graphics.*
import android.hardware.SensorEvent
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.afkoders.chatoyantview.R
import kotlin.math.abs
import kotlin.math.sqrt


/**
 * Created by Kalevych Oleksandr on 13.01.2021.
 */

class ChatoyantSurface(context: Context, attrs: AttributeSet?) : View(context, attrs), ChatoyantStyleable {

    private var cornersRadius = 0f
    private val rectsForClip = arrayListOf<Rect>()
    val paintBackground = Paint()

    val chatoyantHelper: ChatoyantHelper = object : ChatoyantHelper(context) {
        override fun setupHorizontalShader(event: SensorEvent?) {
            val axisY: Float = event?.values?.get(1) ?: 0f

            if (abs(axisY) > MINIMUM_VALUABLE_GYROSCOPE_CHANGE) {
                if (axisY > 0) previousOffset.value += MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT else previousOffset.value -= MINIMUM_VALUABLE_GYROSCOPE_CHANGE * MOVING_COEFICIENT

                setupShader()
                this@ChatoyantSurface.invalidate()
            }
        }
    }

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

            paintBackground.color = getColor(
                R.styleable.ChatoyantView_surface_background_color, ContextCompat.getColor(
                    context,
                    R.color.gold
                )
            )
            cornersRadius = getDimension(R.styleable.ChatoyantView_corners_radius, 0f)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rectsForClip.forEach {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                canvas?.clipOutRect(it)
            } else {
                canvas?.clipRect(it, Region.Op.DIFFERENCE)
            }
        }
        canvas?.drawRoundRect(
            0f,
            0f,
            this.width.toFloat(),
            height.toFloat(),
            cornersRadius,
            cornersRadius,
            paintBackground
        )
        canvas?.drawRoundRect(
            0f,
            0f,
            this.width.toFloat(),
            height.toFloat(),
            cornersRadius,
            cornersRadius,
            chatoyantHelper.paintShader
        )
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
         chatoyantHelper.layout(width)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (visibility) {
            VISIBLE -> chatoyantHelper.onResume()
            else -> chatoyantHelper.onPause()
        }
    }

    fun clipFromViews(vararg innerViews: View) {
        rectsForClip.clear()
        innerViews.filter { it.width > cornersRadius }.forEach { viewToClip ->
            rectsForClip.add(
                Rect(
                    (viewToClip.x - this.x + cornersRadius / sqrt(2.0)).toInt(),
                    (viewToClip.y - this.y + cornersRadius / sqrt(2.0)).toInt(),
                    (viewToClip.width + viewToClip.x - this.x - cornersRadius / sqrt(2.0)).toInt(),
                    (viewToClip.y + viewToClip.height - this.y - cornersRadius / sqrt(2.0)).toInt()
                )
            )
        }
    }

    override fun setAngle(progress: Int) = chatoyantHelper.setAngle(progress)
    override fun setShineWidth(width: Int) = chatoyantHelper.setShineWidthInPercents(width)
    override fun setShineSpeed(speed: Int) = chatoyantHelper.setShineSpeed(speed)
    override fun setRelativeToScreen(relative: Boolean) = chatoyantHelper.setRelativeToScreen(relative)
    override fun setRepeatMode(mode: Int) = chatoyantHelper.setRepeatMode(mode)
    override fun setBitmapForBackground(bitmap: Bitmap?) = chatoyantHelper.setBitmapForBackground(bitmap)

}