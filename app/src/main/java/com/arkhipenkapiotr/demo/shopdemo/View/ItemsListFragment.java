package com.arkhipenkapiotr.demo.shopdemo.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.Model.Item;
import com.arkhipenkapiotr.demo.shopdemo.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 12.02.2018.
 */

public class ItemsListFragment extends Fragment {

    private RecyclerView itemsRecyclerView;
    private ProgressBar progressBar;

    private List<Item> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items_list,container,false);

        items = new ArrayList<>();

        itemsRecyclerView = v.findViewById(R.id.fragment_items_list_recycler_view);
        progressBar = v.findViewById(R.id.fragment_items_list_progress_bar);

        itemsRecyclerView.setAdapter(new RecyclerViewItemsAdapter(items));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        App.getApi().getAllItems().enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() != null) {
                    items.addAll(response.body());
                    itemsRecyclerView.getAdapter().notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(),"Error",Toast.LENGTH_SHORT);
            }
        });

        return v;
    }

    private class RecyclerViewItemsAdapter extends RecyclerView.Adapter<RecyclerViewItemsAdapter.ItemHolder> {

        private List<Item> items;

        private RecyclerViewItemsAdapter(List<Item> hairdressers) {
            this.items = hairdressers;

        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_items_list,parent,false);


            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            holder.bindItem(items.get(position));
        }

        @Override
        public int getItemCount() {
            if (items==null){
                return 0;
            }
            return items.size();
        }

        class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
            ImageView photoImageView;
            TextView nameTextView;
            TextView priceTextView;
            Item item;

            public ItemHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);
                photoImageView = (ImageView) itemView.findViewById(R.id.item_item_list_image_view);
                nameTextView = (TextView) itemView.findViewById(R.id.item_item_list_name_text_view);
                priceTextView = (TextView) itemView.findViewById(R.id.item_item_list_price_text_view);
            }

            public void bindItem(Item item){
                //загрузить фотку
                this.item = item;
                //'Cannot resolve symbol 'Picasso' i don't know why
//                Picasso.with(getContext())
//                        .load(item.getPhotoUrl())
//                        .into(photoImageView);
                nameTextView.setText(item.getName());
                priceTextView.setText(String.valueOf(item.getPrice()));

            }

            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Description")
                        .setMessage(item.getDescription())
                        .setPositiveButton("Заказать",null)
                        .setNegativeButton("Вернуться", null)
                        .create()
                        .show();
            }
        }
    }
}
