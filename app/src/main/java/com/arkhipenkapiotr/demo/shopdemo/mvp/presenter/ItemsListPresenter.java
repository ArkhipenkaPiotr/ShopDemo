package com.arkhipenkapiotr.demo.shopdemo.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.model.Item;
import com.arkhipenkapiotr.demo.shopdemo.mvp.view.ItemListView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 17.02.2018.
 */

@InjectViewState
public class ItemsListPresenter extends MvpPresenter<ItemListView> {

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();

        App.getApi().getAllItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.body()!=null){
                    getViewState().onItemsLoaded(response.body());
                }
                else{
                    getViewState().showServerError();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                getViewState().showServerError();
            }
        });
    }
}
