package com.arkhipenkapiotr.demo.shopdemo.View;

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

import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.Model.Item;
import com.arkhipenkapiotr.demo.shopdemo.Model.ItemOrder;
import com.arkhipenkapiotr.demo.shopdemo.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 12.02.2018.
 */

public class OrderMakerFragment extends Fragment {
    private static final String ITEM_KEY = "item_key";

    private EditText eMailEditText;
    private EditText phoneEditText;
    private Button sendButton;
    private ProgressBar progressBar;

    private ItemOrder itemOrder;


    public static Fragment newInstance(Item item){
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEM_KEY, item);

        Fragment fragment = new OrderMakerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        itemOrder = new ItemOrder();

        Item item = (Item)getArguments().getSerializable(ITEM_KEY);
        itemOrder.setItem(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_maker,container,false);

        eMailEditText = view.findViewById(R.id.fragment_order_maker_email_edit_text);
        phoneEditText = view.findViewById(R.id.fragment_order_maker_phone_edit_text);
        sendButton = view.findViewById(R.id.fragment_order_maker_send_button);
        progressBar = view.findViewById(R.id.fragment_order_maker_progress_bar);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                itemOrder.seteMail(eMailEditText.getText().toString());
                itemOrder.setPhone(phoneEditText.getText().toString());

                App.getApi().postOrder(itemOrder).enqueue(new Callback<ItemOrder>() {
                    @Override
                    public void onResponse(Call<ItemOrder> call, Response<ItemOrder> response) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.thanks_for_your_order), Toast.LENGTH_SHORT).show();
                        popFragment();
                    }

                    @Override
                    public void onFailure(Call<ItemOrder> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), getResources().getString(R.string.order), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return view;
    }

    private void popFragment(){
        getFragmentManager()
                .popBackStack();
    }
}
