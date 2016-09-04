package cn.saymagic.bluefinclient;

import android.app.Application;
import android.content.Context;

import cn.saymagic.bluefinsdk.Bluefin;

/**
 * Created by saymagic on 16/8/29.
 */
public class BluefinApplication extends Application {

    private static BluefinApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Bluefin.init(this, "http://10.242.8.21:2556");
    }

    public static BluefinApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
    }
}
