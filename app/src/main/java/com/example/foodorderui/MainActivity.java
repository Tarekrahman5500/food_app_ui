package com.example.foodorderui;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderui.Activity.CardListActivity;
import com.example.foodorderui.Adapter.CategoryAdapter;
import com.example.foodorderui.Adapter.PopularAdapter;
import com.example.foodorderui.Domain.CategoryDomain;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private RecyclerView.Adapter adapter, adapter2;

    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        recyclerViewCategoryList();
        recyclerViewPopularList();

        binding.CardButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, CardListActivity.class)));
        binding.BottomHomeButton.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, MainActivity.class)));

    }



    private void recyclerViewPopularList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,
                false);

        binding.recyclerView2.setLayoutManager(linearLayoutManager);
        ArrayList<FoodDomain> foodlist = new ArrayList<>();
        foodlist.add(new FoodDomain("Pepperoni pizza", "pizza", "slices pepperoni ,mozzarella cheese, fresh oregano,  ground black pepper, pizza sauce", 9.76));
        foodlist.add(new FoodDomain("Cheese Burger", "burger", "beef, Gouda Cheese, Special sauce, Lettuce, tomato ", 8.79));
        foodlist.add(new FoodDomain("Vegetable pizza", "pizza2", " olive oil, Vegetable oil, pitted Kalamata, cherry tomatoes, fresh oregano, basil", 8.5));

        adapter2 = new PopularAdapter(foodlist);
        binding.recyclerView2.setAdapter(adapter2);
    }

    private void recyclerViewCategoryList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,
                false);

        binding.recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryDomains = new ArrayList<>();
        categoryDomains.add(new CategoryDomain("pizza", "cat_1"));
        categoryDomains.add(new CategoryDomain("Burger", "cat_2"));
        categoryDomains.add(new CategoryDomain("Hotdog", "cat_3"));
        categoryDomains.add(new CategoryDomain("Drink", "cat_4"));
        categoryDomains.add(new CategoryDomain("Donat", "cat_5"));

        adapter = new CategoryAdapter(categoryDomains);
        binding.recyclerView.setAdapter(adapter);
    }
}