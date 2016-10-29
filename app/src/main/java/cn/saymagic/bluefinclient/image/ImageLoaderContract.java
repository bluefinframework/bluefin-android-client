package cn.saymagic.bluefinclient.image;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by saymagic on 16/10/26.
 */
public interface ImageLoaderContract {

    <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, ImageView imageView);

    <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, ImageView imageView);

    <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, int errImg, ImageView imageView);

    <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, Drawable errImg, ImageView imageView);

    ImageLoaderContract INSTANCE = new GlideImageLoader();
}
