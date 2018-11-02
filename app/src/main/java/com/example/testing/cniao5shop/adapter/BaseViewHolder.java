package com.example.testing.cniao5shop.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View>views;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        views=new SparseArray<>();
    }

    public View getView(int id){
        return findView(id);
    }
    private View findView(int id){
        View view = views.get(id);
        if (view!=null){
            view=itemView.findViewById(id);
            views.put(id,view);
        }
        return view;
    }
}
