package cn.saymagic.bluefinclient.data.model;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

import cn.saymagic.bluefinclient.BluefinApplication;
import cn.saymagic.bluefinclient.util.EncryUtil;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;

/**
 * Created by saymagic on 16/9/1.
 */
public class Apk implements Parcelable {

    public String icon;

    public String packageName;

    public int versionCode;

    public String versionName;

    public String name;

    public String apkUrl;

    public String md5;

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
        apk.md5 = data.getFileMd5();
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

    public Apk() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeString(this.packageName);
        dest.writeInt(this.versionCode);
        dest.writeString(this.versionName);
        dest.writeString(this.name);
        dest.writeString(this.apkUrl);
        dest.writeString(this.md5);
        dest.writeLong(this.updateTime);
        dest.writeByte(this.installed ? (byte) 1 : (byte) 0);
        dest.writeParcelable(this.launchIntent, flags);
    }

    protected Apk(Parcel in) {
        this.icon = in.readString();
        this.packageName = in.readString();
        this.versionCode = in.readInt();
        this.versionName = in.readString();
        this.name = in.readString();
        this.apkUrl = in.readString();
        this.md5 = in.readString();
        this.updateTime = in.readLong();
        this.installed = in.readByte() != 0;
        this.launchIntent = in.readParcelable(Intent.class.getClassLoader());
    }

    public static final Creator<Apk> CREATOR = new Creator<Apk>() {
        @Override
        public Apk createFromParcel(Parcel source) {
            return new Apk(source);
        }

        @Override
        public Apk[] newArray(int size) {
            return new Apk[size];
        }
    };
}
