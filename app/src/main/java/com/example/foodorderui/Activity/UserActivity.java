package com.example.foodorderui.Activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import com.example.foodorderui.Helper.Methods;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.R;
import com.example.foodorderui.databinding.ActivityUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    ActivityUserBinding binding;
    FirebaseDatabase database;
   FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        binding.updateUserName.setText(FirebaseAuth.getInstance().getUid());

        // change of my user profile update field any show layout
        Methods.ChangeColor(binding.updatedeatilsText, binding.updateLayout, this);
        // change of my order field any show layout
        Methods.ChangeColor(binding.textView25, binding.ordersLayout, this);
        // change  of payment field any show layout
        Methods.ChangeColor(binding.textView29, binding.paymentLayout, this);
        // change  of Location field any show layout
        Methods.ChangeColor(binding.textView30, binding.locationLayout, this);

        binding.updatePhoneNumber.setText(String.valueOf(880));

        binding.bottomCard.BottomHomeButton.setOnClickListener(v ->
                startActivity(new Intent(UserActivity.this, MainActivity.class)));

       // binding.updatePassword.setText("123456");
        Methods.ShowHidePass(binding.imageId, binding.updatePassword);

        // update the user data

        binding.updateButton.setOnClickListener(this::onClick);

    }



    private void onClick(View v) {
        if (Methods.validatePassword(binding.updatePassword) |
                Methods.validatePhoneNo(binding.updatePhoneNumber) |
                Methods.validateEmail(binding.updateEmail) |
                Methods.validateUserName(binding.updateUserName)
        ) {
            Toast.makeText(this, "Fill all the field correctly", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "User Update", Toast.LENGTH_SHORT).show();
        }
    }
}

