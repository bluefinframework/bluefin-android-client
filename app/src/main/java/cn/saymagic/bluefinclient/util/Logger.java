package cn.saymagic.bluefinclient.util;

import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by saymagic on 16/10/29.
 */
public class Logger {

    public static void logException(String tag, Throwable t) {
        StringBuilder builder = new StringBuilder();
        builder.append(t.getMessage());
        StringWriter writer = new StringWriter();
        t.printStackTrace(new PrintWriter(writer));
        builder.append("\n stack is:\n");
        builder.append(writer.toString());
        Log.e(tag, builder.toString());
    }
}
