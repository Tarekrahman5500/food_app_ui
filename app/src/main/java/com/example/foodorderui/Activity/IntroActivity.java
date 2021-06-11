package com.example.foodorderui.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.databinding.ActivityIntroBinding;

import java.util.Objects;

public class IntroActivity extends AppCompatActivity {
     ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(binding.getRoot());

        binding.ButtonStart.setOnClickListener(v ->
                startActivity(new Intent(IntroActivity.this, MainActivity.class)));
    }

}