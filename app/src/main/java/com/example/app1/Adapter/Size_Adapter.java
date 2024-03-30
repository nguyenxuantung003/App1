package com.example.app1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app1.R;
import com.example.app1.databinding.ViewholderSizeBinding;

import java.util.ArrayList;

public class Size_Adapter extends RecyclerView.Adapter<Size_Adapter.ViewHolder> {
    ArrayList<String> items;
    Context context;
    int selectedPosition = -1;
    int lastselectedPosition = -1;

    public Size_Adapter(ArrayList<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Size_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderSizeBinding binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context),parent,false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Size_Adapter.ViewHolder holder, int position) {
        holder.binding.sizeTxt.setText(items.get(position));
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastselectedPosition = selectedPosition;
                selectedPosition = holder.getAdapterPosition();
                notifyItemChanged(lastselectedPosition);
                notifyItemChanged(selectedPosition);

            }
        });
        if(selectedPosition == holder.getAdapterPosition()){
            holder.binding.sizelayout.setBackgroundResource(R.drawable.size_selected);
            holder.binding.sizeTxt.setTextColor(context.getResources().getColor(R.color.green));

        } else {
            holder.binding.sizelayout.setBackgroundResource(R.drawable.size_unselected);
            holder.binding.sizeTxt.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ViewholderSizeBinding binding;
        public ViewHolder(ViewholderSizeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
