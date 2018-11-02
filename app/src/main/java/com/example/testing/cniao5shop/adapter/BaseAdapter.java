package com.example.testing.cniao5shop.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testing.cniao5shop.R;

import java.util.List;

public abstract class BaseAdapter<T,H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T>mDatas;
    protected LayoutInflater inflater;
    protected Context context;
    protected int mLayoutResId;
    public BaseAdapter(Context context,List<T>datas,int layoutResId){
        this.context=context;
        this.mDatas=datas;
        this.mLayoutResId=layoutResId;
        inflater=LayoutInflater.from(context);
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=inflater.inflate(mLayoutResId,null,false);
        return new BaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        T t = getItem(i);
        bindData(baseViewHolder,t);
    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public T getItem(int position){
        return mDatas.get(position);
    }

    public abstract void bindData(BaseViewHolder baseViewHolder, T t);
}
