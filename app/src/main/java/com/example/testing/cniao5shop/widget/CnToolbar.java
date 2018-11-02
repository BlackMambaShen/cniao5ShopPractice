package com.example.testing.cniao5shop.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.testing.cniao5shop.R;

public class CnToolbar extends Toolbar {
    private LayoutInflater mInflater;
    private TextView toolbar_title;
    private EditText toolbar_searchview;
    private ImageButton toolbar_rightButton;
    private View mView;

    public CnToolbar(Context context) {
        this(context,null);
    }

    public CnToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    @SuppressLint("RestrictedApi")
    public CnToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        setContentInsetsRelative(10,10);
        if (attrs!=null){
            @SuppressLint("RestrictedApi")
            final TintTypedArray a=TintTypedArray.obtainStyledAttributes(getContext(),attrs,
                    R.styleable.CNiaoToolBar,defStyleAttr,0);
            @SuppressLint("RestrictedApi")
            final Drawable rightIcon=a.getDrawable(R.styleable.CNiaoToolBar_rightButtonIcon);
            if (rightIcon!=null){
                setRightButtonIcon(rightIcon);
            }
            boolean isShowSearchView=a.getBoolean(R.styleable.CNiaoToolBar_isShowSearchView,false);
            if (isShowSearchView){
                showSearchView();
                hideTitleView();
            }
            a.recycle();
        }
    }

    private void initView() {
        if (mView==null){
            mInflater=LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.toolbar, null);
            toolbar_title = (TextView)mView.findViewById(R.id.toolbar_title);
            toolbar_searchview = (EditText)mView.findViewById(R.id.toolbar_searchview);
            toolbar_rightButton = (ImageButton)mView.findViewById(R.id.toolbar_rightButton);

            LayoutParams lp=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,Gravity.CENTER_HORIZONTAL);
            addView(mView,lp);
        }
    }

    public void setRightButtonIcon(Drawable icon){
        if (toolbar_rightButton!=null){
            toolbar_rightButton.setImageDrawable(icon);
            toolbar_rightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener listener){
        toolbar_rightButton.setOnClickListener(listener);
    }
    @Override
    public void setTitle(int resId) {
       setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {
            initView();
        if (toolbar_title!=null){
            toolbar_title.setText(title);
            showTitleView();
        }
    }

    public void showSearchView(){
        if (toolbar_searchview!=null){
            toolbar_searchview.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView(){
        if (toolbar_searchview!=null){
            toolbar_searchview.setVisibility(GONE);
        }
    }

    public void hideTitleView(){
        if (toolbar_title!=null){
            toolbar_title.setVisibility(GONE);
        }
    }

    public void showTitleView(){
        if (toolbar_title!=null){
            toolbar_title.setVisibility(VISIBLE);
        }
    }
}
