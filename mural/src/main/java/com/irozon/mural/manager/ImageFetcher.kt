package com.irozon.mural.manager

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

/**
 * Created by Hammad Akram on 3/1/2018.
 */
object ImageFetcher {
    fun decodeSampledBitmapFromUrl(url: URL, reqWidth: Int, reqHeight: Int): Bitmap {

        /*Downloading*/
        var input: InputStream? = getHTTPConnectionInputStream(url)

        /*First decode with inJustDecodeBounds=true to check dimensions*/
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)

        /*Calculate inSampleSize*/
        if (reqHeight != 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        }
        /*Decode bitmap with inSampleSize set*/
        try {
            if (input != null)
                input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        input = getHTTPConnectionInputStream(url)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeStream(input, null, options)

    }

    fun decodeSampledBitmapFromDrawable(context: Context, res: Int, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, res, options)

        // Calculate inSampleSize
        if (reqHeight != 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        }
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(context.resources, res, options)
    }

    private fun getHTTPConnectionInputStream(url: URL): InputStream? {
        val connection: HttpURLConnection?
        try {
            connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            return connection.inputStream
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
