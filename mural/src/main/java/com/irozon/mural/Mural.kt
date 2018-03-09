package com.irozon.mural

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.annotation.AnyRes
import android.support.annotation.DrawableRes
import android.widget.ImageView

import com.irozon.mural.async.LoadBitmapFromDrawableTask
import com.irozon.mural.async.LoadBitmapFromURLTask
import com.irozon.mural.cache.ImageCache
import com.irozon.mural.manager.PlaceHolder
import java.lang.ref.WeakReference


/**
 * Created by Hammad Akram on 3/1/18.
 */

class Mural {
    private var mUrl: String? = null
    private var enableCache = true
    private var mRes: Int = 0
    private var mPlaceHolderBitmap: Bitmap? = null
    private var mWidth = DEFAULT_WIDTH
    private var mHeight = DEFAULT_HEIGHT
    private var cacheAllowed = 1f

    companion object {
        private var contextWeakReference: WeakReference<Context>? = null
        private lateinit var muralWeakReference: WeakReference<Mural>
        var DEFAULT_WIDTH = 500
        var DEFAULT_HEIGHT = 500

        fun with(context: Context): Mural {

            contextWeakReference = WeakReference(context)

            val mural = Mural()
            muralWeakReference = WeakReference(mural)

            DEFAULT_HEIGHT = Utils.getScreenHeight(getContext())!!
            DEFAULT_WIDTH = Utils.getScreenWidth(getContext())!!

            return getMural()
        }
        private fun getContext(): Context? {
            return contextWeakReference?.get()
        }

        private fun getMural(): Mural {
            return muralWeakReference.get()!!
        }
    }

    /**
     * Image source by url
     * @param url
     */
    fun source(url: String): Mural {
        mUrl = url
        mRes = -100
        return getMural()
    }

    /**
     * Image source by drawable resource
     * @param res
     */
    fun source(@DrawableRes res: Int): Mural {
        mRes = res
        mUrl = null
        return getMural()
    }

    /**
     * Resize image
     * @param width
     * @param height
     */
    fun resize(width: Int, height: Int): Mural {
        if (width != 0)
            mWidth = width
        if (height != 0)
            mHeight = height
        return getMural()
    }

    /**
     * Placeholder image
     * @param placeholder - Drawable
     */
    fun placeholder(@AnyRes placeholder: Int): Mural {
        if (placeholder != PlaceHolder.placeHolder) {
            try {
                // Try for drawable resource
                mPlaceHolderBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getContext()?.resources, placeholder), 300, 300, true)
                PlaceHolder.placeHolder = placeholder
                PlaceHolder.placeHolderBitmap = mPlaceHolderBitmap
                PlaceHolder.placeHolderColor = -1
            } catch (ignored: Exception) {
                PlaceHolder.placeHolder = placeholder
                PlaceHolder.placeHolderBitmap = null
                PlaceHolder.placeHolderColor = placeholder
            }

        } else {
            mPlaceHolderBitmap = PlaceHolder.placeHolderBitmap
        }
        return getMural()
    }

    /**
     * Enable cache and set cache size - 0 to 1f
     * @param percent
     */
    fun enableCache(percent: Float): Mural {
        enableCache = true
        cacheAllowed = percent
        return getMural()
    }

    /**
     * Disable cache
     */
    fun disableCache(): Mural {
        enableCache = true
        cacheAllowed = 0f
        return getMural()
    }

    /**
     * Load image to an ImageView
     * @param imageView
     */
    fun loadImage(imageView: ImageView) {
        var imageCache: ImageCache? = null
        var bitmap: Bitmap? = null
        if (enableCache) {
            /*Get imageCache instance*/
            imageCache = ImageCache[getContext(), cacheAllowed]
            var imageKey: String? = null
            if (mUrl != null) {
                imageKey = mUrl
            }
            if (mRes != -100) {
                imageKey = mRes.toString()
            }
            bitmap = imageCache.get(imageKey)
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            if (mUrl != null) { /*Load using url*/
                val task = LoadBitmapFromURLTask(imageView, imageCache, enableCache, mWidth, mHeight, mUrl!!)

                // Set Placeholder
                if (PlaceHolder.placeHolderBitmap != null) { // Placeholder is bitmap
                    imageView.setImageBitmap(PlaceHolder.placeHolderBitmap)
                } else if (PlaceHolder.placeHolderColor != -1) { // Placeholder is color
                    imageView.setImageResource(PlaceHolder.placeHolderColor)
                }

                task.execute()
            }
            if (mRes != -100) { /*Load using Drawable resource*/
                val task = LoadBitmapFromDrawableTask(getContext(), imageView, imageCache, enableCache, mWidth, mHeight, mRes)

                // Set Placeholder
                if (PlaceHolder.placeHolderBitmap != null) { // Placeholder is bitmap
                    imageView.setImageBitmap(PlaceHolder.placeHolderBitmap)
                } else if (PlaceHolder.placeHolderColor != -1) { // Placeholder is color
                    imageView.setImageResource(PlaceHolder.placeHolderColor)
                }

                task.execute()
            }
        }
    }
}