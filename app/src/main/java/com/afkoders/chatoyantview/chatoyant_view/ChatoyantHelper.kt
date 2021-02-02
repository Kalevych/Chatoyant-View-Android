package com.afkoders.chatoyantview.chatoyant_view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.core.content.ContextCompat
import androidx.core.graphics.transform
import com.afkoders.chatoyantview.R
import com.afkoders.chatoyantview.RangedFloat
import com.afkoders.chatoyantview.gyroscope.GyroscopeProvider
import kotlin.math.abs

/**
 * Created by Kalevych Oleksandr on 20.01.2021.
 */
abstract class ChatoyantHelper(private val context: Context) : SensorEventListener {

    var bitmap: Bitmap? = null
    var bitmapMode = false
    val gyroscopeProvider: GyroscopeProvider
        get() = GyroscopeProvider.instance

    override fun onSensorChanged(event: SensorEvent?) {
        if (event != null) setupHorizontalShader(event)
    }

    var previousOffset = RangedFloat(MIN_OFFSET_VALUE, MAX_OFFSET_VALUE)
    private var screenWidth: Int = 0
    private var screenHeight: Int = 0
    private var angle = 0.0 //in radians
    private var isRelativeDimension = false // for making all the views synchronized on the screen
    private var shineWidth = 0.0f
    private var shineColor = ContextCompat.getColor(context, R.color.halfWhite)
    private var stepForAnimation: Float = 0f
    private lateinit var tileMode: Shader.TileMode

    val paintShader = Paint()

    abstract fun setupHorizontalShader(event: SensorEvent?)

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    internal fun setupShader(value: Float = previousOffset.value) {
        if (bitmapMode) {
            return setupBitmapShader()
        } else {

            var purposedHeight = (shineWidth * Math.tan(angle)).toFloat()
            if (purposedHeight > screenHeight) purposedHeight = abs(value) * stepForAnimation
            if (purposedHeight < - screenHeight) purposedHeight = abs(value) * -stepForAnimation

            paintShader.shader = LinearGradient(
                0f + abs(value) * stepForAnimation,
                0f,
                abs(value) * stepForAnimation + shineWidth,
                purposedHeight,
                Color.TRANSPARENT,
                shineColor,
                tileMode
            )
        }
    }

    internal fun setupShaderWithTextColor(
        value: Float = previousOffset.value,
        textColor: Int
    ) {
        paintShader.shader = LinearGradient(
            0f + abs(value) * stepForAnimation,
            0f,
            abs(value) * stepForAnimation + shineWidth,
            (shineWidth * Math.tan(angle)).toFloat(),
            textColor,
            shineColor,
            tileMode
        )
    }

    internal fun setupBitmapShader(value: Float = previousOffset.value) {
        var purposedHeightShift = (value * shineWidth) * Math.tan(angle)
        if (purposedHeightShift > screenHeight) purposedHeightShift = screenHeight / 2.0
        if (purposedHeightShift < -screenHeight) purposedHeightShift = -screenHeight / 2.0

        val shader = BitmapShader(bitmap!!, tileMode, Shader.TileMode.CLAMP)
        shader.transform {
            setTranslate(
                value * shineWidth,
                purposedHeightShift.toFloat()
            )
        }
        paintShader.shader = shader
    }

    fun layout(width: Int) {
        if (!isRelativeDimension && shineWidth <= 1) {
            shineWidth *= width
        }
        setupShader()
    }

    fun setupGyroscope(context: Context) {
        gyroscopeProvider.onInit(context, this)
    }

    fun onResume() {
        gyroscopeProvider.onResumeView(this)
    }

    fun onPause() {
        gyroscopeProvider.onPauseView(this)
    }

    fun parseAttributes(typedArray: TypedArray, point: Point) {

        screenWidth = point.x
        screenHeight = point.y

        typedArray.getInteger(R.styleable.ChatoyantView_shine_angle, 0).run {
            angle = (this * Math.PI / 180)
        }

        isRelativeDimension =
            typedArray.getBoolean(R.styleable.ChatoyantView_is_relative_dimension, true)

        shineWidth = typedArray.getFloat(R.styleable.ChatoyantView_shine_width, 0.333f).run {
            if (isRelativeDimension) {
                screenWidth * this
            } else this
        }

        shineColor = typedArray.getColor(
            R.styleable.ChatoyantView_shine_color,
            ContextCompat.getColor(context, R.color.halfWhite)
        )

        stepForAnimation = (screenWidth.toFloat() / 15) * typedArray.getFloat(
            R.styleable.ChatoyantView_animation_velocity,
            1f
        )

        tileMode = when (typedArray.getInteger(R.styleable.ChatoyantView_tile_mode, 0)) {
            1 -> Shader.TileMode.REPEAT
            2 -> Shader.TileMode.MIRROR
            else -> Shader.TileMode.CLAMP
        }
    }


    fun setAngle(angle: Int) {
        this.angle = (angle * Math.PI / 180)
    }

    // TAKING 0 to 100 (PERCENTS
    fun setShineWidthInPercents(shineWidth: Int) {
        this.shineWidth = if (isRelativeDimension) {
            screenWidth * shineWidth.toFloat() / 100
        } else shineWidth.toFloat() / 100
    }

    fun setShineSpeed(speed: Int) {
        this.stepForAnimation = (screenWidth.toFloat() / 15)
        this.stepForAnimation *= speed
    }

    fun setRelativeToScreen(isRelative: Boolean) {
        this.isRelativeDimension = isRelative
    }

    fun setRepeatMode(repeatMode: Int) {
        this.tileMode = when (repeatMode) {
            1 -> Shader.TileMode.REPEAT
            2 -> Shader.TileMode.MIRROR
            else -> Shader.TileMode.CLAMP
        }
    }

    fun setBitmapForBackground(bitmap: Bitmap?){
        this.bitmap = bitmap
        bitmapMode = bitmap !=null
    }

    companion object {
        const val MINIMUM_VALUABLE_GYROSCOPE_CHANGE = 0.07f
        const val MOVING_COEFICIENT = 2
        const val MAX_OFFSET_VALUE = 6f
        const val MIN_OFFSET_VALUE = 0f
    }
}