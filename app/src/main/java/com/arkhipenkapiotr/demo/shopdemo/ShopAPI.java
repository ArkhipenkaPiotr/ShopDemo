package com.arkhipenkapiotr.demo.shopdemo;

import com.arkhipenkapiotr.demo.shopdemo.Model.Item;
import com.arkhipenkapiotr.demo.shopdemo.Model.ItemOrder;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by arhip on 12.02.2018.
 */

public interface ShopAPI {

    @GET
    public List<Item> getAllItems();

    @POST
    public ItemOrder postOrder(ItemOrder itemOrder);
}
