package com.arkhipenkapiotr.demo.shopdemo.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arkhipenkapiotr.demo.shopdemo.model.Item;

import java.util.List;

/**
 * Created by arhip on 17.02.2018.
 */

public interface ItemListView extends MvpView {
    void onItemsLoaded(List<Item> items);
    void showServerError();
}
