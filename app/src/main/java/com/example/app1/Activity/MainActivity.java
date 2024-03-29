package com.example.app1.Activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;

import com.example.app1.Adapter.SliderAdapter;
import com.example.app1.Domain.Slider_Items;
import com.example.app1.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initBaner();
        
    }

    private void initBaner() {
        DatabaseReference myref = database.getReference("Banner");
        binding.progressBarBanner.setVisibility(View.VISIBLE);
        ArrayList<Slider_Items> items = new ArrayList<>();
        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Slider_Items.class));

                    }
                    banner(items);
                    binding.progressBarBanner.setVisibility(View.GONE);

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void banner(ArrayList<Slider_Items> items) {
        binding.viewpagerSlider.setAdapter(new SliderAdapter(items,binding.viewpagerSlider));
        binding.viewpagerSlider.setClipToPadding(false);
        binding.viewpagerSlider.setClipChildren(false);
        binding.viewpagerSlider.setOffscreenPageLimit(3);
        binding.viewpagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        binding.viewpagerSlider.setPageTransformer(compositePageTransformer);


    }
}