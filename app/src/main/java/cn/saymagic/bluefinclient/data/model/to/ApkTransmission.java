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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeString(this.name);
        dest.writeString(this.apkUrl);
    }

    public ApkTransmission() {
    }

    protected ApkTransmission(Parcel in) {
        this.packageName = in.readString();
        this.name = in.readString();
        this.apkUrl = in.readString();
    }

    public static final Parcelable.Creator<ApkTransmission> CREATOR = new Parcelable.Creator<ApkTransmission>() {
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
