package cn.saymagic.bluefinclient.util;

import android.content.res.Resources;

/**
 * Created by saymagic on 16/9/1.
 */
public class UIUtil {

    public static float px2dp(float px) {
        float densityDpi = Resources.getSystem().getDisplayMetrics().densityDpi;
        return px / (densityDpi / 160f);
    }

    public static int dp2px(int dp) {
        float density = Resources.getSystem().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}
