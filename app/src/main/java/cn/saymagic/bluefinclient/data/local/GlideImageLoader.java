package cn.saymagic.bluefinclient.data.local;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by saymagic on 16/10/26.
 */
public class GlideImageLoader extends BaseImageLoader {

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, ImageView imageView) {
        super.load(context, remoteUrl, defaultImg, imageView);
        Glide.with(context).load(remoteUrl).placeholder(defaultImg).into(imageView);
    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, int defaultImg, int errImg, ImageView imageView) {
        super.load(context, remoteUrl, defaultImg, errImg, imageView);
        Glide.with(context).load(remoteUrl).placeholder(defaultImg).error(errImg).into(imageView);
    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, ImageView imageView) {
        super.load(context, remoteUrl, defaultImg, imageView);
        Glide.with(context).load(remoteUrl).placeholder(defaultImg).into(imageView);
    }

    @Override
    public <LoadType> void load(Context context, LoadType remoteUrl, Drawable defaultImg, Drawable errImg, ImageView imageView) {
        super.load(context, remoteUrl, defaultImg, errImg, imageView);
        Glide.with(context).load(remoteUrl).placeholder(defaultImg).error(errImg).into(imageView);
    }
}
