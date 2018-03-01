package com.irozon.mural.manager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Hammad Akram on 3/1/2018.
 */
public class ImageFetcher {
    public static Bitmap decodeSampledBitmapFromUrl(URL url, int reqWidth, int reqHeight) {

        /*Downloading*/
        InputStream input;

        input = getHTTPConnectionInputStream(url);

        /*First decode with inJustDecodeBounds=true to check dimensions*/
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(input, null, options);

        /*Calculate inSampleSize*/
        if (reqHeight != 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }
        /*Decode bitmap with inSampleSize set*/
        try {
            if (input != null)
                input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        input = getHTTPConnectionInputStream(url);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeStream(input, null, options);

    }

    public static Bitmap decodeSampledBitmapFromDrawable(Context context, int res, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(), res, options);

        // Calculate inSampleSize
        if (reqHeight != 0) {
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        }
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(context.getResources(), res, options);
    }

    private static InputStream getHTTPConnectionInputStream(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            return connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
