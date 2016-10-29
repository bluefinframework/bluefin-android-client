package cn.saymagic.bluefinclient.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import cn.saymagic.bluefinclient.data.model.to.ApkTransmission;
import cn.saymagic.bluefinclient.ui.apk.ApkActivity;
import cn.saymagic.bluefinclient.ui.download.ApkDownloadActivity;
import cn.saymagic.bluefinclient.ui.login.LoginActivity;

/**
 * Created by saymagic on 16/9/2.
 */
public class UIController {

    public static final String EXTRA_APK_TRANSMISSION = "EXTRA_APK_TRANSMISSION";

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

    public static void openDownloadActivity(@NonNull Context context, ApkTransmission transmission) {
        Intent intent = new Intent(context, ApkDownloadActivity.class);
        intent.putExtra(EXTRA_APK_TRANSMISSION, transmission);
        context.startActivity(intent);
    }

    public static void showToast(@NonNull Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void finishActivity(@NonNull Activity activity) {
        activity.finish();
    }
}
