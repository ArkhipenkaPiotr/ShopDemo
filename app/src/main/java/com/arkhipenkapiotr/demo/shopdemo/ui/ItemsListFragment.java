package com.arkhipenkapiotr.demo.shopdemo.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arkhipenkapiotr.demo.shopdemo.App;
import com.arkhipenkapiotr.demo.shopdemo.model.Item;
import com.arkhipenkapiotr.demo.shopdemo.R;
import com.arkhipenkapiotr.demo.shopdemo.mvp.presenter.ItemsListPresenter;
import com.arkhipenkapiotr.demo.shopdemo.mvp.view.ItemListView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by arhip on 12.02.2018.
 */

public class ItemsListFragment extends MvpAppCompatFragment implements ItemListView {

    @BindView(R.id.fragment_items_list_recycler_view)
    RecyclerView itemsRecyclerView;

    @BindView(R.id.fragment_items_list_progress_bar)
    ProgressBar progressBar;

    @BindString(R.string.server_error)
    String serverError;

    @InjectPresenter
    ItemsListPresenter itemsListPresenter;

    private List<Item> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_items_list,container,false);

        ButterKnife.bind(this, v);

        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onItemsLoaded(List<Item> items) {
        progressBar.setVisibility(View.GONE);
        this.items = items;

        itemsRecyclerView.setAdapter(new RecyclerViewItemsAdapter(items));
        itemsRecyclerView.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void showServerError() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(getContext(), serverError, Toast.LENGTH_SHORT).show();
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
                this.item = item;
                Picasso.with(getContext())
                        .load(item.getPhotoUrl())
                        .into(photoImageView);
                nameTextView.setText(item.getName());
                priceTextView.setText(String.valueOf(item.getPrice()));

            }

            @Override
            public void onClick(View view) {
                getDescriptionDialog(item).show();
            }
        }
    }

    private DialogInterface.OnClickListener getPositiveButtonOnClickListener(final Item item){
        DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, OrderMakerFragment.newInstance(item))
                        .addToBackStack(null)
                        .commit();
            }
        };
        return onClickListener;
    }

    private AlertDialog getDescriptionDialog(Item item){
        return new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.description))
                .setMessage(item.getDescription())
                .setPositiveButton(getResources().getString(R.string.make_an_order),getPositiveButtonOnClickListener(item))
                .setNegativeButton(getResources().getString(R.string.back), null)
                .create();
    }
}
