package com.example.foodorderui.Activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Helper.ManagementCard;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.databinding.ActivityShowDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class ShowDetailActivity extends AppCompatActivity {
    private FoodDomain object;
    private ManagementCard managementCard;
    private  int NumberOfOrder = 1;
   ActivityShowDetailBinding binding;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        // show the admin panel if the user is admin
        MainActivity mainActivity = new MainActivity();
       // mainActivity.ShowAdminPanel(databaseReference, binding.bottomCard, ShowDetailActivity.this);
       managementCard = new ManagementCard(this);
        getBundle();
    }

    private void getBundle() {

        // object come from popular adapter
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