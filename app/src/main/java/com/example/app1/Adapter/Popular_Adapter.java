package com.example.app1.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.app1.Domain.Items_Domain;
import com.example.app1.databinding.ViewholderPopListBinding;

import java.util.ArrayList;

public class Popular_Adapter extends RecyclerView.Adapter<Popular_Adapter.ViewHolder> {
    ArrayList<Items_Domain> items;
    Context context;

    public Popular_Adapter(ArrayList<Items_Domain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Popular_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderPopListBinding binding = ViewholderPopListBinding.inflate(LayoutInflater.from(context),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Popular_Adapter.ViewHolder holder, int position) {
        holder.binding.title.setText(items.get(position).getTitle());
        holder.binding.reviewTxt.setText(""+items.get(position).getReview());
        holder.binding.PriceTxt.setText("$"+items.get(position).getPrice());
        holder.binding.ratingTxt.setText("("+items.get(position).getRating()+")");
        holder.binding.oldPricetxt.setText("$"+items.get(position).getOldPrice());
        holder.binding.oldPricetxt.setPaintFlags(holder.binding.oldPricetxt.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        holder.binding.ratingBar.setRating((float) items.get(position).getRating());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());
        Glide.with(context)
                .load(items.get(position).getPicUrl().get(0))
                .apply(requestOptions)
                .into(holder.binding.pic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewholderPopListBinding binding;

        public ViewHolder(ViewholderPopListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
