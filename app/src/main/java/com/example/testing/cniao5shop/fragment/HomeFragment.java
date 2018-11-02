package com.example.testing.cniao5shop.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.testing.cniao5shop.R;
import com.example.testing.cniao5shop.adapter.HomeAdapter;
import com.example.testing.cniao5shop.bean.Banner;
import com.example.testing.cniao5shop.bean.Campaign;
import com.example.testing.cniao5shop.bean.HomeCampaign;
import com.example.testing.cniao5shop.http.BaseCallback;
import com.example.testing.cniao5shop.http.OkHttpHelper;
import com.example.testing.cniao5shop.widget.Contants;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HomeFragment extends Fragment {

    private static final String TAG = "xiaoliang";
    private RecyclerView rec_home;
    private Gson mGson=new Gson();
    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        private List<HomeCampaign> homeCampaigns;
        private List<Banner> banners;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                   banners= (List<Banner>) msg.obj;
                    break;
                case 2:
                    homeCampaigns= (List<HomeCampaign>) msg.obj;
                    break;
            }
            HomeAdapter adapter = new HomeAdapter(getActivity(),banners,homeCampaigns);
            adapter.setOnCampaignClickListener(new HomeAdapter.OnCampaignClickListener() {
                @Override
                public void onCLick(View view, Campaign campaign) {
                    Toast.makeText(getActivity(), "title="+campaign.getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            rec_home.setLayoutManager(new LinearLayoutManager(getActivity()));
            rec_home.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
            rec_home.setAdapter(adapter);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }
    private OkHttpHelper httpHelper=OkHttpHelper.getInstance();
    public void requestImages(){
        String url="http://112.124.22.238:8081/course_api/banner/query?type=1";
        httpHelper.get(url, new BaseCallback<List<Banner>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, final List<Banner> banners) {
                Message msg=Message.obtain();
                msg.what=1;
                msg.obj=banners;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
         rec_home = (RecyclerView)view.findViewById(R.id.rec_home);
         requestImages();
         requsetHome();
    }

    private void requsetHome() {
        httpHelper.get(Contants.API.CAMPAIGN_HOME, new BaseCallback<List<HomeCampaign>>() {
            @Override
            public void onRequestBefore(Request request) {

            }

            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {
                Message msg=Message.obtain();
                msg.what=2;
                msg.obj=homeCampaigns;
                handler.sendMessage(msg);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
