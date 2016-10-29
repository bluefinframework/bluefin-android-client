package cn.saymagic.bluefinclient.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by saymagic on 16/10/26.
 */
public class BaseImageLoader implements ImageLoaderContract {

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, ImageView imageView) {

    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, ImageView imageView) {

    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, int errImg, ImageView imageView) {

    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, Drawable errImg, ImageView imageView) {

    }

}
