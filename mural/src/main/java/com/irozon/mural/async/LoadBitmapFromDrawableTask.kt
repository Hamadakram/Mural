package com.irozon.mural.async

import android.content.Context
import android.widget.ImageView
import com.irozon.mural.cache.ImageCache
import com.irozon.mural.manager.ImageFetcher
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by Hammad Akram on 3/1/2018.
 */
class LoadBitmapFromDrawableTask(private val mContext: Context?, private val imageView: ImageView, private val mCache: ImageCache?, private val mCacheImage: Boolean, private val mWidth: Int, private val mHeight: Int, var res: Int) {
    fun execute() {
        doAsync {
            val bitmap = mContext?.let { ImageFetcher.decodeSampledBitmapFromDrawable(it, res, mWidth, mHeight) }
            uiThread {
                if (mCacheImage) {
                    mCache?.put(res.toString(), bitmap)
                }
                imageView.setImageBitmap(bitmap)
            }
        }
    }
}

