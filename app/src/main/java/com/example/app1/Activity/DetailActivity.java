package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.app1.Adapter.Size_Adapter;
import com.example.app1.Adapter.SliderAdapter;
import com.example.app1.Domain.Items_Domain;
import com.example.app1.Domain.Slider_Items;
import com.example.app1.Helper.ManagmentCart;
import com.example.app1.R;
import com.example.app1.databinding.ActivityDetailBinding;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Items_Domain obj;
    private int numberOder = 1;
    private ManagmentCart managmentCart;
    private Handler slideHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

        getBundler();
        banners();
        initSize();

        
    }

    private void initSize() {
        ArrayList<String> list = new ArrayList<>();
        list.add("S");
        list.add("M");
        list.add("L");
        list.add("XL");
        list.add("XXL");

        binding.recyclerSize.setAdapter(new Size_Adapter(list));
        binding.recyclerSize.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));


    }

    private void banners() {
        ArrayList<Slider_Items> sliderItems = new ArrayList<>();
        for (int i = 0; i < obj.getPicUrl().size(); i++) {
                sliderItems.add(new Slider_Items(obj.getPicUrl().get(i)));
        }
        binding.viewpageSlider.setAdapter(new SliderAdapter(sliderItems,binding.viewpageSlider));
        binding.viewpageSlider.setClipToPadding(false);
        binding.viewpageSlider.setClipChildren(false);
        binding.viewpageSlider.setOffscreenPageLimit(3);
        binding.viewpageSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
    }

    private void getBundler() {
        obj = (Items_Domain) getIntent().getSerializableExtra("object");
        binding.titleTxt.setText(obj.getTitle());
        binding.priceTxt.setText("$"+ obj.getPrice());
        binding.ratingTxt.setText(obj.getRating() + "rating");
        binding.addTocartBtn.setOnClickListener(v -> {
            obj.setNumberInCart(numberOder);
            managmentCart.insertFood(obj);
        });
        binding.backBtn.setOnClickListener(v -> finish());
    }
}