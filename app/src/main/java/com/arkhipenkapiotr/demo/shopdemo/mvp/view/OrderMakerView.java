package com.arkhipenkapiotr.demo.shopdemo.mvp.view;

import com.arellomobile.mvp.MvpView;

/**
 * Created by arhip on 17.02.2018.
 */

public interface OrderMakerView extends MvpView {

    void onSuccesfulOrder();
    void showServerError();
}
