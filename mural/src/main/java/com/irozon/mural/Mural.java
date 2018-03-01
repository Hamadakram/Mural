package com.irozon.mural;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.AnyRes;
import android.widget.ImageView;

import com.irozon.mural.async.LoadBitmapFromDrawableTask;
import com.irozon.mural.async.LoadBitmapFromURLTask;
import com.irozon.mural.cache.ImageCache;
import com.irozon.mural.manager.PlaceHolder;

/**
 * Created by Hammad Akram on 3/1/18.
 */

public class Mural {
    private static Context mContext;
    private String mUrl;
    static Mural mural;
    private boolean enableCache = false;
    private int mRes;
    private Bitmap mPlaceHolderBitmap;
    public static int DEFAULT_WIDTH = 500;
    private int mWidth = DEFAULT_WIDTH;
    public static int DEFAULT_HEIGHT = 500;
    private int mHeight = DEFAULT_HEIGHT;
    private float cacheAllowed = 0;


    public static Mural getMural() {
        return new Mural();
    }

    public static Mural with(Context context) {
        mContext = context;
        DEFAULT_HEIGHT = Utils.getScreenHeight(mContext);
        DEFAULT_WIDTH = Utils.getScreenWidth(mContext);
        mural = getMural();
        return mural;
    }

    /*using Url*/
    public Mural source(String url) {
        mUrl = url;
        mRes = -100;
        return mural;
    }

    /*using drawable resource*/
    public Mural source(int res) {
        mRes = res;
        mUrl = null;
        return mural;
    }

    /*Resize image*/
    public Mural resize(int width, int height) {
        if (width != 0)
            mWidth = width;
        if (height != 0)
            mHeight = height;
        return mural;
    }

    /*PlaceHolder image*/
    public Mural placeholder(@AnyRes int placeholder) {
        if (placeholder != PlaceHolder.getPlaceHolder()) {
            try {
                // Try for drawable resource
                mPlaceHolderBitmap = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(mContext.getResources(), placeholder), 300, 300, true);
                PlaceHolder.setPlaceHolder(placeholder);
                PlaceHolder.setPlaceHolderBitmap(mPlaceHolderBitmap);
                PlaceHolder.setPlaceHolderColor(-1);
            } catch (Exception ignored) {
                PlaceHolder.setPlaceHolder(placeholder);
                PlaceHolder.setPlaceHolderBitmap(null);
                PlaceHolder.setPlaceHolderColor(placeholder);
            }
        } else {
            mPlaceHolderBitmap = PlaceHolder.getPlaceHolderBitmap();
        }
        return mural;
    }

    /*Enable cache*/
    public Mural enableCache(float percent) {
        enableCache = true;
        cacheAllowed = percent;
        return mural;
    }

    /*Load Image*/
    public void loadImage(ImageView imageView) {
        ImageCache imageCache = null;
        Bitmap bitmap = null;
        if (enableCache) {
            /*Get imageCache instance*/
            imageCache = ImageCache.get(mContext, cacheAllowed);
            String imageKey = null;
            if (mUrl != null) {
                imageKey = mUrl;
            }
            if (mRes != -100) {
                imageKey = String.valueOf(mRes);
            }
            bitmap = imageCache.get(imageKey);
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            if (mUrl != null) { /*Load using url*/
                final LoadBitmapFromURLTask task = new LoadBitmapFromURLTask(imageView, imageCache, enableCache, mWidth, mHeight);

                //final AsyncDrawable asyncDrawable = new AsyncDrawable(mPlaceHolderBitmap, task);

                // Set Placeholder
                if (PlaceHolder.getPlaceHolderBitmap() != null) { // Placeholder is bitmap
                    imageView.setImageBitmap(PlaceHolder.getPlaceHolderBitmap());
                } else if (PlaceHolder.getPlaceHolderColor() != -1) { // Placeholder is color
                    imageView.setImageResource(PlaceHolder.getPlaceHolderColor());
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mUrl);
                else
                    task.execute(mUrl);
            }
            if (mRes != -100) { /*Load using Drawable resource*/
                final LoadBitmapFromDrawableTask task = new LoadBitmapFromDrawableTask(mContext, imageView, imageCache, enableCache, mWidth, mHeight);
                //final AsyncDrawable asyncDrawable = new AsyncDrawable(mPlaceHolderBitmap, task);
                //imageView.setImageDrawable(asyncDrawable);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
                    task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, mRes);
                else
                    task.execute(mRes);
            }
        }
    }
}