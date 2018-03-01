package com.irozon.mural.cache;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by Hammad Akram on 3/1/2018.
 */
public class ImageCache extends LruCache<String, Bitmap> {

    private static ImageCache instance;

    public ImageCache(int maxSize) {
        super(maxSize);
    }

    public static ImageCache get(Context context, float cacheAllowed) {
        if (instance == null) {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            float memClassBytes = am.getMemoryClass() * 1024 * 1024;
            float cacheSize = (memClassBytes * cacheAllowed);
            instance = new ImageCache(Math.round(cacheSize));
        }
        return instance;
    }

    @Override
    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }
}
