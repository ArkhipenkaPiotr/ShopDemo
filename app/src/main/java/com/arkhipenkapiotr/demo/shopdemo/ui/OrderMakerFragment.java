package com.arkhipenkapiotr.demo.shopdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.model.Item;
import com.arkhipenkapiotr.demo.shopdemo.model.ItemOrder;
import com.arkhipenkapiotr.demo.shopdemo.R;
import com.arkhipenkapiotr.demo.shopdemo.mvp.presenter.OrderMakerPresenter;
import com.arkhipenkapiotr.demo.shopdemo.mvp.view.OrderMakerView;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 12.02.2018.
 */

public class OrderMakerFragment extends MvpAppCompatFragment implements OrderMakerView {
    private static final String ITEM_KEY = "item_key";

    @InjectPresenter
    OrderMakerPresenter orderMakerPresenter;

    @BindView(R.id.fragment_order_maker_email_edit_text)
    EditText eMailEditText;

    @BindView(R.id.fragment_order_maker_phone_edit_text)
    EditText phoneEditText;

    @BindView(R.id.fragment_order_maker_send_button)
    Button sendButton;

    @BindView(R.id.fragment_order_maker_progress_bar)
    ProgressBar progressBar;

    @BindString(R.string.thanks_for_your_order)
    String thanks;

    @BindString(R.string.server_error)
    String serverError;

    public static Fragment newInstance(Item item){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_KEY, item);

        Fragment fragment = new OrderMakerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_maker,container,false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.fragment_order_maker_send_button)
    public void sendOrder(){
        progressBar.setVisibility(View.VISIBLE);

        Item orderItem = (Item) getArguments().getSerializable(ITEM_KEY);
        orderMakerPresenter.sendOrder(orderItem, eMailEditText.getText().toString(), phoneEditText.getText().toString());
    }

    private void popFragment(){
        getFragmentManager()
                .popBackStack();
    }

    @Override
    public void onSuccesfulOrder() {
        Toast.makeText(getContext(), thanks, Toast.LENGTH_SHORT).show();
        popFragment();
    }

    @Override
    public void showServerError() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), serverError, Toast.LENGTH_SHORT).show();
    }
}
