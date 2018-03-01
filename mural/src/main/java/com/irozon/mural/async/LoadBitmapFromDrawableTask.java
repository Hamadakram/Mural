package com.irozon.mural.async;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.irozon.mural.cache.ImageCache;
import com.irozon.mural.manager.ImageFetcher;

import java.lang.ref.WeakReference;

/**
 * Created by Hammad Akram on 3/1/2018.
 */
public class LoadBitmapFromDrawableTask extends AsyncTask<Integer, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final ImageCache mCache;
    private final boolean mCacheImage;
    private final Context mContext;
    private final int mWidth;
    private final int mHeight;
    public int res;

    public LoadBitmapFromDrawableTask(Context context, ImageView imageView, ImageCache cache, boolean cacheImage, int width, int height) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        mContext = context;
        mCache = cache;
        mCacheImage = cacheImage;
        mWidth = width;
        mHeight = height;
    }

    @Override
    protected Bitmap doInBackground(Integer... resource) {
        res = resource[0];
        return ImageFetcher.decodeSampledBitmapFromDrawable(mContext, res, mWidth, mHeight);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (bitmap != null) {
            if (mCacheImage) {
                mCache.put(String.valueOf(res), bitmap);
            }
            final ImageView imageView = imageViewReference.get();
            if (imageView != null)
                imageView.setImageBitmap(bitmap);
        }
    }
}


