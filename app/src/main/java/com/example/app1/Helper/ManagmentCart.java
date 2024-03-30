package com.example.app1.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.app1.Domain.Items_Domain;

import java.util.ArrayList;

public class ManagmentCart {

    private Context context;
    private TinyDB tinyDB;

    public ManagmentCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Items_Domain item) {
        ArrayList<Items_Domain> listfood = getListCart();
        boolean existAlready = false;
        int n = 0;
        for (int y = 0; y < listfood.size(); y++) {
            if (listfood.get(y).getTitle().equals(item.getTitle())) {
                existAlready = true;
                n = y;
                break;
            }
        }
        if (existAlready) {
            listfood.get(n).setNumberInCart(item.getNumberInCart());
        } else {
            listfood.add(item);
        }
        tinyDB.putListObject("CartList", listfood);
        Toast.makeText(context, "Added to your Cart", Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Items_Domain> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusItem(ArrayList<Items_Domain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        if (listfood.get(position).getNumberInCart() == 1) {
            listfood.remove(position);
        } else {
            listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() - 1);
        }
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public void plusItem(ArrayList<Items_Domain> listfood, int position, ChangeNumberItemsListener changeNumberItemsListener) {
        listfood.get(position).setNumberInCart(listfood.get(position).getNumberInCart() + 1);
        tinyDB.putListObject("CartList", listfood);
        changeNumberItemsListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<Items_Domain> listfood2 = getListCart();
        double fee = 0;
        for (int i = 0; i < listfood2.size(); i++) {
            fee = fee + (listfood2.get(i).getPrice() * listfood2.get(i).getNumberInCart());
        }
        return fee;
    }
}
