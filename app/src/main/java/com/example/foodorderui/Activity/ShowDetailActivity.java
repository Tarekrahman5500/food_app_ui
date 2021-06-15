package com.example.foodorderui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Helper.ManagementCard;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivityShowDetailBinding;

import java.util.Objects;

public class ShowDetailActivity extends AppCompatActivity {
    private FoodDomain object;
    private ManagementCard managementCard;
    private  int NumberOfOrder = 1;
   ActivityShowDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
       managementCard = new ManagementCard(this);
        getBundle();
    }

    private void getBundle() {
        object = (FoodDomain) getIntent().getSerializableExtra("object");
        int drawableResourceId = this.getResources().getIdentifier(object.getPic()
                , "drawable", this.getPackageName());

        Glide.with(this).load(drawableResourceId).into(binding.foodPic);
        binding.titleText.setText(object.getTitle());
        binding.priceText.setText("$".concat(object.getFee().toString()));
        binding.descriptionText.setText(object.getDescription());
        binding.NumberOrderText.setText(String.valueOf(NumberOfOrder));

        binding.ButtonPlus.setOnClickListener(v -> {
            NumberOfOrder = NumberOfOrder + 1;
            binding.NumberOrderText.setText(String.valueOf(NumberOfOrder));
        });

        binding.ButtonMinus.setOnClickListener(v -> {
            if (NumberOfOrder == 1) {
                binding.ButtonMinus.setEnabled(false);
            }
            binding.ButtonMinus.setEnabled(true);
            if (NumberOfOrder > 1) {
                NumberOfOrder = NumberOfOrder - 1;
            }
            binding.NumberOrderText.setText(String.valueOf(NumberOfOrder));
        });

        binding.ButtonAddtoCard.setOnClickListener(v -> {
            object.setNumberInCard(NumberOfOrder);
            managementCard.insertFood(object);
        });
    }
}