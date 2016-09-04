package cn.saymagic.bluefinclient.data.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

import cn.saymagic.bluefinclient.BluefinApplication;
import cn.saymagic.bluefinclient.util.EncryUtil;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;

/**
 * Created by saymagic on 16/9/1.
 */
public class Apk {

    public String icon;

    public String packageName;

    public int versionCode;

    public String versionName;

    public String name;

    public String apkUrl;

    public long updateTime;

    public boolean installed;

    public Intent launchIntent;

    public static Apk from( @NonNull BluefinApkData data) {
        Apk apk = new Apk();
        Context context = BluefinApplication.getContext();
        apk.icon = data.getIconUrl();
        apk.packageName = data.getPackageName();
        apk.versionCode = data.getVersionCode();
        apk.versionName = data.getVersionName();
        apk.name = data.getName();
        apk.apkUrl = data.getDownloadUrl();
        apk.updateTime = data.getUpdateTime();
        apk.installed = isInstalled(context, data);
        apk.launchIntent = getLaunchIntent(context, data);
        return apk;
    }

    private static Intent getLaunchIntent(@NonNull Context context, @NonNull BluefinApkData data) {
        PackageManager packageManager = context.getPackageManager();
        return packageManager.getLaunchIntentForPackage(data.getPackageName());
    }

    private static boolean isInstalled(@NonNull Context context, @NonNull BluefinApkData data) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(data.getPackageName(), 0);
            if (packageInfo == null || packageInfo.versionCode != data.getVersionCode() || !TextUtils.equals(data.getVersionName(), packageInfo.versionName)) {
                return false;
            }
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            String source = applicationInfo.sourceDir;
            File file = new File(source);
            return TextUtils.equals(EncryUtil.getMD5(file), data.getFileMd5());
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

}
