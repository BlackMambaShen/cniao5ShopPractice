package com.example.testing.cniao5shop.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.testing.cniao5shop.R;
import com.example.testing.cniao5shop.adapter.BaseAdapter;
import com.example.testing.cniao5shop.adapter.BaseViewHolder;
import com.example.testing.cniao5shop.adapter.HotWaresAdapter;
import com.example.testing.cniao5shop.bean.Page;
import com.example.testing.cniao5shop.bean.Wares;
import com.example.testing.cniao5shop.http.BaseCallback;
import com.example.testing.cniao5shop.http.OkHttpHelper;
import com.example.testing.cniao5shop.widget.Contants;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;
import okhttp3.Response;

public class HotFragment extends Fragment {
    private OkHttpHelper httpHelper=OkHttpHelper.getInstance();
    private int currPage=1;
    private int pageSize=10;
    private HotWaresAdapter adapter;
    private RecyclerView recyclerView;
//    private List<Wares> datas;
    public static final int STATE_NORMAL=0;
    public static final int STATE_REFRESH=1;
    public static final int STATE_MORE=2;
    private int state=STATE_NORMAL;
    private MaterialRefreshLayout refreshLayout;
    private int totalPage=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
         refreshLayout=(MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        getData();
        initRefreshLayout();
    }

    private void getData(){
        String url=Contants.API.WARES_HOT+"?curPage="+currPage+"&pageSize="+pageSize;
        httpHelper.get(url, new BaseCallback<Page<Wares>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                List<Wares> datas = waresPage.getList();
                currPage=waresPage.getCurrentPage();
                totalPage=waresPage.getTotalPage();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                },2000);
                showData(datas);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    private void initRefreshLayout(){
        refreshLayout.setLoadMore(true);
        refreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                    refreshData();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                    if (currPage<=totalPage){
                        loadMoreData();
                    }else {
                        Toast.makeText(getContext(), "已经没有下一页数据了!", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    private void refreshData(){
        currPage=1;
        state=STATE_REFRESH;
        getData();
    }

    private void loadMoreData(){
        currPage=currPage+1;
        state=STATE_MORE;
        getData();
    }


    private void showData(List<Wares> datas){
        switch (state){
            case STATE_NORMAL:
                adapter=new HotWaresAdapter(datas,getActivity());
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//                recyclerView.setAdapter(new BaseAdapter<Wares,BaseViewHolder>(getActivity(),datas,R.layout.template_hot_wares) {
//
//                    @Override
//                    public void bindData(BaseViewHolder baseViewHolder, Wares wares) {
//                        ImageView drawee_view = (ImageView) baseViewHolder.getView(R.id.drawee_view);
//                        Glide.with(getActivity()).load(wares.getImgUrl()).into(drawee_view);
//                        TextView text_title = (TextView) baseViewHolder.getView(R.id.text_title);
//                    }
//                });
                break;

            case STATE_REFRESH:
                adapter.clearData();
                adapter.addData(datas);
                recyclerView.scrollToPosition(0);
                refreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                adapter.addData(adapter.getDatas().size(),datas);
                recyclerView.scrollToPosition(adapter.getDatas().size());
                refreshLayout.finishRefreshLoadMore();
                break;
        }
    }
}
