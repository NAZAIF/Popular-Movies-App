package com.example.android.popularmoviesapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;

/**
 * Created by nazaif on 21/12/17.
 */

public class ColorUtils {

    public static int getViewHolderBackgroundColorFromInstance(Context context, int instanceNum) {
        switch (instanceNum % 10) {
            case 0:
                return ContextCompat.getColor(context, R.color.c1);
            case 1:
                return ContextCompat.getColor(context, R.color.c2);
            case 2:
                return ContextCompat.getColor(context, R.color.c3);
            case 3:
                return ContextCompat.getColor(context, R.color.c4);
            case 4:
                return ContextCompat.getColor(context, R.color.c5);
            case 5:
                return ContextCompat.getColor(context, R.color.c6);
            case 6:
                return ContextCompat.getColor(context, R.color.c7);
            case 7:
                return ContextCompat.getColor(context, R.color.c8);
            case 8:
                return ContextCompat.getColor(context, R.color.c9);
            case 9:
                return ContextCompat.getColor(context, R.color.c10);

            default:
                return Color.WHITE;
        }
    }
}