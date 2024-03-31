package com.example.app1.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.app1.Adapter.Size_Adapter;
import com.example.app1.Adapter.SliderAdapter;
import com.example.app1.Domain.Items_Domain;
import com.example.app1.Domain.Slider_Items;
import com.example.app1.Fragment.Description_Fragment;
import com.example.app1.Fragment.Review_Fragment;
import com.example.app1.Fragment.Sold_Fragment;
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
        setupViewPager();

        
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
    private void setupViewPager(){
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());
        Description_Fragment tab1 = new Description_Fragment();
        Review_Fragment tab2 = new Review_Fragment();
        Sold_Fragment tab3 = new Sold_Fragment();
        Bundle bundle1 = new Bundle();
        Bundle bundle2 = new Bundle();
        Bundle bundle3 = new Bundle();
        bundle1.putString("description",obj.getDescription());
        tab1.setArguments(bundle1);
        tab2.setArguments(bundle2);
        tab3.setArguments(bundle3);

        adapter.addFragment(tab1,"Description");
        adapter.addFragment(tab2,"Review");
        adapter.addFragment(tab3,"Sold");
        binding.viewpager.setAdapter(adapter);
         binding.taplayout.setupWithViewPager(binding.viewpager);

    }
    private class ViewPagerAdapter extends FragmentPagerAdapter{
        private final List<Fragment> mFragmentList = new ArrayList<>();
         private final  List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position) ;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        private void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
        @Override
        public CharSequence getPageTitle(int position){
            return mFragmentTitleList.get(position);
        }
    }

}