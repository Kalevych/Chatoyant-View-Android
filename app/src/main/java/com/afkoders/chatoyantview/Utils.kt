package com.afkoders.chatoyantview

import android.content.Context
import android.util.DisplayMetrics

/**
 * Created by Kalevych Oleksandr on 14.12.2020.
 */

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
fun convertDpToPixel(dp: Float, context: Context): Float {
    return dp * (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
fun convertPixelsToDp(px: Float, context: Context): Float {
    return px / (context.resources.displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Float.toDp(context: Context): Float {
    return convertPixelsToDp(this, context)
}

fun Float.toPx(context: Context): Float {
    return convertDpToPixel(this, context)
}


class RangedFloat(val min: Float, val max: Float){
    internal var value: Float = min
    set(value) {
        if (value in min..max) field = value
    }
}