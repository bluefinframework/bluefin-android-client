package cn.saymagic.bluefinclient.data.cache;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import cn.saymagic.bluefinclient.BluefinApplication;

/**
 * Created by saymagic on 16/9/4.
 */
public class SpCacheManager implements CacheContract {

    private static volatile SpCacheManager INSTANCE;

    public static SpCacheManager getInstance() {
        if (INSTANCE == null) {
            synchronized (SpCacheManager.class) {
                INSTANCE = new SpCacheManager();
            }
        }
        return INSTANCE;
    }

    private SpCacheManager() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(BluefinApplication.getContext());
    }

    private SharedPreferences mPreferences;


    @Override
    public void save(String key, String value) {
        if (mPreferences != null && !TextUtils.isEmpty(key)) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    @Override
    public String get(String key) {
        return mPreferences.getString(key, "");
    }
}
