package com.example.foodorderui.Activity;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivityLoginBinding;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        binding.ForgotPasword.getPaint().setUnderlineText(true);

        binding.CallLogin.setOnClickListener(v -> {

            binding.LoginLayout.setVisibility(View.VISIBLE);
            binding.SignUpLayout.setVisibility(View.GONE);

            binding.CallSignUp.setTextColor(ContextCompat.getColor(this, R.color.LogoText));
            binding.CallLogin.setTextColor(ContextCompat.getColor(this, R.color.white));
        });
        binding.CallSignUp.setOnClickListener(v -> {
            binding.LoginLayout.setVisibility(View.GONE);
            binding.SignUpLayout.setVisibility(View.VISIBLE);

            binding.CallSignUp.setTextColor(ContextCompat.getColor(this, R.color.white));
            binding.CallLogin.setTextColor(ContextCompat.getColor(this, R.color.LogoText));
        });

        binding.signUpGoogle.setOnClickListener(v -> Toast.makeText(this, "google", Toast.LENGTH_SHORT).show());
        binding.signUpFB.setOnClickListener(v -> Toast.makeText(this, "FB", Toast.LENGTH_SHORT).show());

    }
}