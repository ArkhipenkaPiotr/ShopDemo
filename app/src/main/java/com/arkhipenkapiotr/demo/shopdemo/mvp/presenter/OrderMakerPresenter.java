package com.arkhipenkapiotr.demo.shopdemo.mvp.presenter;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.R;
import com.arkhipenkapiotr.demo.shopdemo.model.Item;
import com.arkhipenkapiotr.demo.shopdemo.model.ItemOrder;
import com.arkhipenkapiotr.demo.shopdemo.mvp.view.OrderMakerView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 17.02.2018.
 */

@InjectViewState
public class OrderMakerPresenter extends MvpPresenter<OrderMakerView> {
    public void sendOrder(Item item, String eMail, String phoneNumber){
        ItemOrder itemOrder = new ItemOrder();
        itemOrder.seteMail(eMail);
        itemOrder.setPhone(phoneNumber);
        itemOrder.setItem(item);

        App.getApi().postOrder(itemOrder).enqueue(new Callback<ItemOrder>() {
            @Override
            public void onResponse(Call<ItemOrder> call, Response<ItemOrder> response) {
                if (response.body()!=null){
                    getViewState().onSuccesfulOrder();
                }
                else {
                    getViewState().showServerError();
                }
            }

            @Override
            public void onFailure(Call<ItemOrder> call, Throwable t) {
                getViewState().showServerError();
            }
        });
    }
}
