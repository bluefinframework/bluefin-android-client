package cn.saymagic.bluefinclient.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by saymagic on 16/9/4.
 */
public class DateUtil {

    public static String getDateString(Date date) {
        if (date == null) {
            return "";
        }
        String pattern = "yyyy-MM-dd HH:mm EEE";
        Locale locale = Locale.getDefault();
        if (locale.equals(Locale.CHINA)
                || locale.equals(Locale.TRADITIONAL_CHINESE)) {
            pattern = "yyyy年MM月dd日 HH:mm EEEEEE";
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

}
