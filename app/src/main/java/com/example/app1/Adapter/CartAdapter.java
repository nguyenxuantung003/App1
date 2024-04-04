package com.example.app1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.app1.Domain.Items_Domain;
import com.example.app1.Helper.ChangeNumberItemsListener;
import com.example.app1.Helper.ManagmentCart;
import com.example.app1.databinding.ViewholderCartBinding;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    ArrayList<Items_Domain> listItemsSelected;
    ChangeNumberItemsListener changeNumberItemsListener;
    private ManagmentCart managmentCart;

    public CartAdapter(ArrayList<Items_Domain> listItemsSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemsSelected = listItemsSelected;
        this.changeNumberItemsListener = changeNumberItemsListener;
        managmentCart = new ManagmentCart(context);
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewholderCartBinding binding =ViewholderCartBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.binding.titleTxt.setText(listItemsSelected.get(position).getTitle());
        holder.binding.feeEachItem.setText("$" +listItemsSelected.get(position).getPrice());
        holder.binding.totalEachItem.setText("$" + Math.round((listItemsSelected.get(position).getNumberInCart() + listItemsSelected.get(position).getPrice())));
        holder.binding.numberItemsTxt.setText(String.valueOf(listItemsSelected.get(position).getNumberInCart()));

        RequestOptions requestOptions =new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());
        Glide.with(holder.itemView.getContext())
                .load(listItemsSelected.get(position).getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.binding.pic);
        holder.binding.plusCartBtn.setOnClickListener(v -> managmentCart.plusItem(listItemsSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.binding.minusCartBtn.setOnClickListener(v -> managmentCart.minusItem(listItemsSelected, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));


    }

    @Override
    public int getItemCount() {
        return listItemsSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewholderCartBinding binding;
        public ViewHolder(ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
