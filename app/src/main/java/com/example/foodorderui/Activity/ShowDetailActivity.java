package com.example.foodorderui.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.foodorderui.databinding.ActivityShowDetailBinding;

import java.util.Objects;

public class ShowDetailActivity extends AppCompatActivity {
   ActivityShowDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
    }
}