package com.irozon.mural.manager;

import android.graphics.Bitmap;

/**
 * Created by Hammad Akram on 3/1/2018.
 */
public class PlaceHolder {
    private static int mPlaceHolder;
    private static Bitmap mPlaceHolderBitmap;
    private static int mPlaceHolderColor;

    public static int getPlaceHolder() {
        return mPlaceHolder;
    }

    public static void setPlaceHolder(int mPlaceHolder) {
        PlaceHolder.mPlaceHolder = mPlaceHolder;
    }

    public static Bitmap getPlaceHolderBitmap() {
        return mPlaceHolderBitmap;
    }

    public static void setPlaceHolderBitmap(Bitmap mPlaceHolderBitmap) {
        PlaceHolder.mPlaceHolderBitmap = mPlaceHolderBitmap;
    }

    public static int getPlaceHolderColor() {
        return mPlaceHolderColor;
    }

    public static void setPlaceHolderColor(int mPlaceHolderColor) {
        PlaceHolder.mPlaceHolderColor = mPlaceHolderColor;
    }
}
