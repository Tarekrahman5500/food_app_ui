package com.example.foodorderui.Activity;

import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderui.Adapter.CardListAdapter;
import com.example.foodorderui.Helper.ManagementCard;
import com.example.foodorderui.Interface.ChangeNumberItemListener;
import com.example.foodorderui.MainActivity;
import com.example.foodorderui.databinding.ActivityCardListBinding;

import java.util.Objects;

public class CardListActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter;
    private ManagementCard managementCard;
    ActivityCardListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Objects.requireNonNull(getSupportActionBar()).hide();

        managementCard = new ManagementCard(this);

        initList();
        calculatedCard();

        binding.bottomCard.CardButton.setOnClickListener(v ->
                startActivity(new Intent(CardListActivity.this, CardListActivity.class)));
        binding.bottomCard.BottomHomeButton.setOnClickListener(v ->
                startActivity(new Intent(CardListActivity.this, MainActivity.class)));

    }

    private void initList() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.recycleview.setLayoutManager(linearLayoutManager);
        adapter = new CardListAdapter(managementCard.getListCard(), this, this::calculatedCard);


        binding.recycleview.setAdapter(adapter);
        if (managementCard.getListCard().isEmpty()) {
            binding.emptyText.setVisibility(View.VISIBLE);
            binding.scrollView3.setVisibility(View.GONE);
        } else {
            binding.emptyText.setVisibility(View.GONE);
            binding.scrollView3.setVisibility(View.VISIBLE);
        }
    }

    private void calculatedCard() {
        double percentTax = 0.02;
        double delivery = 10;

        double tax = Math.round((managementCard.getTotalFee() * percentTax) * 100.0) / 100.0;
        double total = Math.round((managementCard.getTotalFee() + tax + delivery) * 100.0) / 100.0;
        double itemTotal = Math.round(managementCard.getTotalFee() * 100.0) / 100.0;

        binding.TotalItemFeeText.setText("$".concat(String.valueOf(itemTotal)));
        binding.TaxTextFee.setText("$".concat(String.valueOf(tax)));
        binding.deliveryTextFee.setText("$".concat(String.valueOf(delivery)));
        binding.TotalTextFee.setText("$".concat(String.valueOf(total)));
    }
}