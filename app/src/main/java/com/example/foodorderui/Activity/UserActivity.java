package com.example.foodorderui.Activity;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.foodorderui.databinding.ActivityUserBinding;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.textView12.setOnClickListener(v -> binding.textView14.setVisibility((
                binding.textView14.getVisibility() == View.VISIBLE) ?
                View.GONE : View.VISIBLE));
    }
}