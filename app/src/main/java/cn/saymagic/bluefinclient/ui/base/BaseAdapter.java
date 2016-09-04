package cn.saymagic.bluefinclient.ui.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

/**
 * Created by saymagic on 16/9/1.
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mDatas;

    public BaseAdapter(Context context, List<T> datas) {
        this.mContext = context;
        this.mDatas = datas == null ? Collections.<T>emptyList() : datas;
    }

    public void setData(List<T> data) {
        this.mDatas = data;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
