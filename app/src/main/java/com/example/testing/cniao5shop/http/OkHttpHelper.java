package com.example.testing.cniao5shop.http;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkHttpHelper {
    private static OkHttpClient okHttpClient;
    private Gson gson=new Gson();
    private OkHttpHelper(){
        okHttpClient=new OkHttpClient();
    }

    public static OkHttpHelper getInstance(){
        return new OkHttpHelper();
    }

    public void get(String url,BaseCallback callback){
        Request request = bulidRequest(url, null, HttpMethodType.GET);
        doRequest(request,callback);
    }

    public void post(String url, Map<String,String>params,BaseCallback callback){
        Request request = bulidRequest(url, params, HttpMethodType.POST);
        doRequest(request,callback);
    }

    public void doRequest(final Request request, final BaseCallback callback){
        callback.onRequestBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(request,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String resultStr = response.body().string();
                    if (callback.mType==String.class){
                        callback.onSuccess(response,resultStr);
                    }else {
                        try {
                            Object object = gson.fromJson(resultStr, callback.mType);
                            callback.onSuccess(response,object);
                        }catch (Exception e){
                            callback.onError(response,response.code(),e);
                        }
                    }
                }else {
                    callback.onError(response,response.code(),null);
                }
            }
        });
    }

    private Request bulidRequest(String url,Map<String,String>params,HttpMethodType methodType){
        Request.Builder builder=new Request.Builder();
        builder.url(url);
        if (methodType==HttpMethodType.GET){
            builder.get();
        }
        else if (methodType==HttpMethodType.POST){
            RequestBody body=buildFormData(params);
            builder.post(body);
        }

        return builder.build();
    }

    private RequestBody buildFormData(Map<String,String>params){
        FormBody.Builder builder = new FormBody.Builder();
        if (params!=null){
            for (Map.Entry<String,String> entry:params.entrySet()){
                builder.add(entry.getKey(),entry.getValue());
            }
        }
        return builder.build();
    }

    enum HttpMethodType{
        GET,
        POST
    }
}
