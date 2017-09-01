package com.sundaypyjamas.sundaypyjamas_simplestepscounter.Util;

import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by saumyamehta on 8/31/17.
 */

public class Util extends Application{
    public static void setRalewayThin(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Regular.ttf");
        textView.setTypeface(typeface);
    }

    public static void setRalewayThin(Context context, TextView... textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Regular.ttf");
        for(TextView tv: textView)
        {
            tv.setTypeface(typeface);
        }
    }
    public static void setRalewayExtraThin(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Thin.ttf");
        textView.setTypeface(typeface);
    }

    public static void setRalewayExtraThin(Context context, TextView... textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Thin.ttf");
        for(TextView tv: textView)
        {
            tv.setTypeface(typeface);
        }
    }
    public static void setRalewayBold(Context context, TextView textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Bold.ttf");
        textView.setTypeface(typeface);
    }

    public static void setRalewayBold(Context context, TextView... textView) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),"fonts/Raleway-Bold.ttf");
        for(TextView tv: textView)
        {
            tv.setTypeface(typeface);
        }
    }

}
