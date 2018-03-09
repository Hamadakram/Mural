package com.irozon.mural

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint

/**
 * Created by hammad.akram on 3/1/18.
 */

object Utils {

    fun getScreenHeightWithStatusBar(context: Context): Int {
        val dm = context.resources.displayMetrics
        return dm.heightPixels
    }

    fun getScreenHeight(context: Context?): Int? {
        val dm = context?.resources?.displayMetrics
        return dm?.heightPixels?.minus(getStatusBarHeight(context))
    }

    fun getScreenWidth(context: Context?): Int? {
        val dm = context?.resources?.displayMetrics
        return dm?.widthPixels
    }

    fun getStatusBarHeight(context: Context?): Int {
        var result = 0
        val resourceId = context?.resources?.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId != null) {
            if (resourceId > 0) {
                result = context.resources.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    fun createImage(width: Int, height: Int, color: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = color
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        return bitmap
    }
}
