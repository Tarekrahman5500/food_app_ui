package com.example.foodorderui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.foodorderui.Adapter.PopularAdapter;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.databinding.ActivityPizzaBinding;

import java.util.ArrayList;
import java.util.Objects;

public class PizzaActivity extends AppCompatActivity {
    ActivityPizzaBinding binding;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityPizzaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerViewCategoryList();
    }

    private void recyclerViewCategoryList() {

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
                2, StaggeredGridLayoutManager.VERTICAL);
        binding.rectlerview.setLayoutManager(staggeredGridLayoutManager);
        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Pepperoni pizza", "pizza", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 9.76));
        foodlist.add(new FoodDomain("Cheese Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 8.79));
       // foodlist.add(new FoodDomain("Vegetable pizza", "pizza2", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 8.5));

        adapter = new PopularAdapter(foodlist);
        binding.rectlerview.setAdapter(adapter);
    }
}