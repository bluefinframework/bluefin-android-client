package cn.saymagic.bluefinclient.ui.apk;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

import cn.saymagic.bluefinclient.data.model.Apk;
import cn.saymagic.bluefinclient.ui.base.BaseAdapter;

/**
 * Created by saymagic on 16/8/31.
 */
public class ApkAdapter extends BaseAdapter<Apk, ApkAdapter.AppViewHolder> {


    public ApkAdapter(Context context, List<Apk> datas) {
        super(context, datas);
    }

    @Override
    public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AppViewHolder(new ApkItemView(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(AppViewHolder holder, int position) {
        holder.bind(mDatas.get(position));
    }

    static class AppViewHolder extends RecyclerView.ViewHolder {

        private ApkItemView itemView;

        public AppViewHolder(ApkItemView itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void bind(Apk apk) {
            this.itemView.bind(apk);
        }
    }
}
