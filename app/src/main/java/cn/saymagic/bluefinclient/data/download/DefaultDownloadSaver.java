package cn.saymagic.bluefinclient.data.download;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;

import java.io.File;

import cn.saymagic.bluefinclient.util.EncryUtil;

/**
 * Created by saymagic on 16/11/5.
 */
public class DefaultDownloadSaver implements DownloadSaveContract {

    private Context mContext;

    private static final String ROOT_DIRECTORY = Environment
            .getExternalStorageDirectory().getAbsolutePath();

    private static final String ROOT_DIRECTORY_DOWNLOAD = ROOT_DIRECTORY
            + File.separator + "Bluefin";

    public DefaultDownloadSaver(@NonNull Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public File getSaveFile(String url, Object... params) {
        return getSaveFile(url, "", params);
    }

    @Override
    public File getSaveFile(String url, String suffix, Object... params) {
        if (url == null) {
            throw new IllegalStateException("download url is empty!");
        }
        File file = new File(ROOT_DIRECTORY_DOWNLOAD);
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
        } else {
            file.mkdirs();
        }
        String md5 = EncryUtil.getMD5(url);
        File ret = new File(file, md5 + "." + suffix);
        return ret;
    }
}
