package com.example.foodorderui.Activity;

import android.content.Intent;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.R;
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
        // change of my user profile update field any show layout
        ChangeColor( binding.updatedeatilsText,binding.updateLayout);
        // change of my order field any show layout
        ChangeColor( binding.textView25,binding.ordersLayout);
        // change  of payment field any show layout
        ChangeColor( binding.textView29,binding.paymentLayout);
        // change  of Location field any show layout
        ChangeColor( binding.textView30,binding.locationLayout);

        binding.bootomCard.BottomHomeButton.setOnClickListener(v ->
            startActivity(new Intent(UserActivity.this, MainActivity.class)));

        binding.updatePassword.setText("123456");
        LoginActivity.ShowHidePass(binding.imageId, binding.updatePassword);

    }

    private void ChangeColor(TextView p1, LinearLayout p2) {
        p1.setOnClickListener(v -> {
            if (p2.getVisibility() == View.VISIBLE){
               p2.setVisibility(View.GONE);
               p1.setTextColor(ContextCompat.getColor(this, R.color.LogoText));
            } else {
                p2.setVisibility(View.VISIBLE);
               p1.setTextColor(ContextCompat.getColor(this, R.color.white));
            }
        });
    }
}

