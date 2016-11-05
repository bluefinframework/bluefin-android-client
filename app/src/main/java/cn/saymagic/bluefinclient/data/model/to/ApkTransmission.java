package cn.saymagic.bluefinclient.data.model.to;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by saymagic on 16/10/27.
 */
public class ApkTransmission implements Parcelable {

    public String packageName;

    public String name;

    public String apkUrl;

    public String md5;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public ApkTransmission() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.name);
        dest.writeString(this.apkUrl);
        dest.writeString(this.md5);
    }

    protected ApkTransmission(Parcel in) {
        this.packageName = in.readString();
        this.name = in.readString();
        this.apkUrl = in.readString();
        this.md5 = in.readString();
    }

    public static final Creator<ApkTransmission> CREATOR = new Creator<ApkTransmission>() {
        @Override
        public ApkTransmission createFromParcel(Parcel source) {
            return new ApkTransmission(source);
        }

        @Override
        public ApkTransmission[] newArray(int size) {
            return new ApkTransmission[size];
        }
    };
}
