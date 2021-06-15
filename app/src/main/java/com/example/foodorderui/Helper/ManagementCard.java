package com.example.foodorderui.Helper;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Interface.ChangeNumberItemListener;

import java.util.ArrayList;

public class ManagementCard {
    private final Context context;
    private final TinyDB tinyDB;

    public ManagementCard(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(FoodDomain item) {
        ArrayList<FoodDomain> listFood = getListCard();
        boolean existAlready = false;
        int n = 0;
        int i = 0;
        if (i < listFood.size()) {
            do {
                if (listFood.get(i).getTitle().equals(item.getTitle())) {
                    existAlready = true;
                    n = i;
                    break;
                }
                i++;
            } while (i < listFood.size());
        }
        if (existAlready) {
            listFood.get(n).setNumberInCard(item.getNumberInCard());
        } else {
            listFood.add(item);
        }

        tinyDB.putListObject("CardList", listFood);
        Toast toast = Toast.makeText(context, "Added To Your Card", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();


    }

    public ArrayList<FoodDomain> getListCard() {
        return tinyDB.getListObject("CardList");
    }

    public  void plusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {
        listFood.get(position).setNumberInCard(listFood.get(position).getNumberInCard()+ 1);
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemListener.changed();
    }

    public void minusNumberFood(ArrayList<FoodDomain> listFood, int position, ChangeNumberItemListener changeNumberItemListener) {
        if (listFood.get(position).getNumberInCard() == 1 ) {
            listFood.remove(position);
        } else {
            listFood.get(position).setNumberInCard(listFood.get(position).getNumberInCard() - 1 );
        }
        tinyDB.putListObject("CardList", listFood);
        changeNumberItemListener.changed();
    }

    public Double getTotalFee() {
        ArrayList<FoodDomain> listFood2 = getListCard();

        double fee = 0;
        for (FoodDomain foodDomain : listFood2) {
            fee = fee + (foodDomain.getFee() * foodDomain.getNumberInCard());
        }
        return  fee;
    }
}
