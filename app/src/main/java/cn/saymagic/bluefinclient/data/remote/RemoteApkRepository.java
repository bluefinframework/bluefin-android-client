package cn.saymagic.bluefinclient.data.remote;

import java.util.List;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.rx.NetworkStateTransformer;
import cn.saymagic.bluefinrxsdk.BluefinRxAdapter;
import cn.saymagic.bluefinsdk.entity.BluefinApkData;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by saymagic on 16/9/1.
 */
public class RemoteApkRepository implements ApkRepositoryContract {

    private volatile static RemoteApkRepository instance;

    public static RemoteApkRepository getInstance() {
        if (instance == null) {
            synchronized (RemoteApkRepository.class) {
                instance = new RemoteApkRepository();
            }
        }
        return instance;
    }

    private RemoteApkRepository() {
    }

    @Override
    public Observable<Apk> loadAllApks() {
        return BluefinRxAdapter.listAllApks().compose(new NetworkStateTransformer<List<BluefinApkData>>()).concatMap(new Func1<List<BluefinApkData>, Observable<? extends BluefinApkData>>() {
            @Override
            public Observable<BluefinApkData> call(List<BluefinApkData> bluefinApkDatas) {
                return Observable.from(bluefinApkDatas);
            }
        }).map(new Func1<BluefinApkData, Apk>() {
            @Override
            public Apk call(BluefinApkData data) {
                return Apk.from(data);
            }
        });
    }
}
