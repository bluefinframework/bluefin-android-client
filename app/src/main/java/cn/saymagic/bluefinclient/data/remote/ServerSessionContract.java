package cn.saymagic.bluefinclient.data.remote;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinsdk.entity.PingResult;
import rx.Observable;

/**
 * Created by saymagic on 16/9/7.
 */
public interface ServerSessionContract {

    Observable<PingResult> login(final String serverUrl);

    void saveServerUrl(String serverUrl);

    String getServerUrl();

    Observable<PingResult> loginLast();

    Observable<Apk> loadAllApks();

    ServerSession INSTANCE = ServerSession.getInstance();

}
