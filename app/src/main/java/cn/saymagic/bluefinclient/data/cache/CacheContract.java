package cn.saymagic.bluefinclient.data.cache;

/**
 * Created by saymagic on 16/9/4.
 */
public interface CacheContract {

    void save(String key, String value);

    String get(String key);

    CacheContract INSTANCE = CacheManager.getInstance();

}
