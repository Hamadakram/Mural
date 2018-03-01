package com.irozon.mural.async;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.irozon.mural.cache.ImageCache;
import com.irozon.mural.manager.ImageFetcher;

import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Hammad Akram on 3/1/2018.
 */
public class LoadBitmapFromURLTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final ImageCache mCache;
    private final boolean mCacheImage;
    private final int mWidth;
    private final int mHeight;
    public String strUrl;

    public LoadBitmapFromURLTask(ImageView imageView, ImageCache cache, boolean cacheImage, int width, int height) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        mCache = cache;
        mCacheImage = cacheImage;
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        strUrl = urls[0];
        URL url = null;
        try {
            url = new URL(strUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return ImageFetcher.decodeSampledBitmapFromUrl(url, mWidth, mHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (mCacheImage) {
                mCache.put(strUrl, bitmap);
            }
            final ImageView imageView = imageViewReference.get();
            if (imageView != null)
                imageView.setImageBitmap(bitmap);

        }
    }
}

