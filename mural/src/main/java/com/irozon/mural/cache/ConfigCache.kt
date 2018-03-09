package com.irozon.mural.cache

import android.util.Log

/**
 * Created by Hammad Akram on 3/1/2018.
 */
object ConfigCache {
    fun setMemCacheSizePercent(percent: Float): Float {
        if (percent > 1f) {
            Log.w("Cache size warning", "Cache should be less than 1(100%). Setting 25%")
            return 25f
        }
        return percent
    }
}
