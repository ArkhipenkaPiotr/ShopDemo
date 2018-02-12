package com.arkhipenkapiotr.demo.shopdemo;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by arhip on 12.02.2018.
 */

public class App extends Application {

    private Retrofit retrofit;
    private static ShopAPI shopAPI = null;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.180.4:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        shopAPI = retrofit.create(ShopAPI.class);
    }

    public static ShopAPI getApi(){
        return shopAPI;
    }
}
