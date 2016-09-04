package cn.saymagic.bluefinclient.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import cn.saymagic.bluefinclient.ui.apk.ApkActivity;
import cn.saymagic.bluefinclient.ui.login.LoginActivity;

/**
 * Created by saymagic on 16/9/2.
 */
public class UIController {

    public static void openApkActivity(@NonNull Context context) {
        Intent intent = new Intent(context, ApkActivity.class);
        context.startActivity(intent);
    }

    public static void openLoginActivity(@NonNull Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void openActivityByIntent(@NonNull Context context, @NonNull Intent intent) {
        context.startActivity(intent);
    }
}
