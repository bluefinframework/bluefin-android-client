package cn.saymagic.bluefinclient.data.download;

import java.io.File;

import cn.saymagic.bluefinclient.BluefinApplication;

/**
 * Created by saymagic on 16/11/5.
 */
public interface DownloadSaveContract {

    File getSaveFile(String url, Object... params);

    File getSaveFile(String url, String suffix, Object... params);

    DownloadSaveContract DEFAULT = new DefaultDownloadSaver(BluefinApplication.getInstance());
}
