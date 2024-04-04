package com.example.app1.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.app1.Adapter.CartAdapter;
import com.example.app1.Helper.ChangeNumberItemsListener;
import com.example.app1.Helper.ManagmentCart;
import com.example.app1.R;
import com.example.app1.databinding.ActivityCartBinding;

public class CartActivity extends AppCompatActivity {
    ActivityCartBinding binding;
    private double tax;
    private ManagmentCart managmentCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        managmentCart = new ManagmentCart(this);

         caculartoCart();
         setvarialbe();
         initCartList();

    }

    private void initCartList() {
        if(managmentCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);

        } else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        binding.cartview.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        binding.cartview.setAdapter(new CartAdapter(managmentCart.getListCart(), this, () -> caculartoCart()));
    }

    private void setvarialbe() {
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void caculartoCart() {
        double percentTax = 0.02;
        double delivery = 10;
        tax=Math.round((managmentCart.getTotalFee() * percentTax * 100.0)) / 100.0;
        double total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100) / 100;
        double itemtotal = Math.round(managmentCart.getTotalFee() * 100 ) / 100;
        binding.totalFreeTxt.setText("$" + itemtotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }
}