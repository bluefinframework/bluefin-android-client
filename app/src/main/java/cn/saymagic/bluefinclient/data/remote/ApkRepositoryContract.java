package cn.saymagic.bluefinclient.data.remote;

import cn.saymagic.bluefinclient.data.model.Apk;
import rx.Observable;

/**
 * Created by saymagic on 16/9/1.
 */
public interface ApkRepositoryContract {

    Observable<Apk> loadAllApks();

    ApkRepositoryContract INSTANCE = RemoteApkRepository.getInstance();

}
