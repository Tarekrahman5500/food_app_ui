package com.example.foodorderui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.foodorderui.Activity.CardListActivity;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.Helper.ManagementCard;
import com.example.foodorderui.Interface.ChangeNumberItemListener;
import com.example.foodorderui.R;

import java.util.ArrayList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.ViewHolder> {

    private final ArrayList<FoodDomain> foodDomains;
    private final ManagementCard managementCart;
    private final ChangeNumberItemListener changeNumberItemsListener;

    public CardListAdapter(ArrayList<FoodDomain> FoodDomains, Context context, ChangeNumberItemListener changeNumberItemsListener) {

        this.foodDomains = FoodDomains;
        managementCart = new ManagementCard(context);
       this.changeNumberItemsListener =  changeNumberItemsListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_card, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(foodDomains.get(position).getTitle());
        holder.feeForEachItem.setText(String.valueOf(foodDomains.get(position).getFee()));
        holder.totalEachItem.setText(String.valueOf(Math.round((foodDomains.get(position).getNumberInCard() * foodDomains.get(position).getFee()) * 100.0) / 100.0));
        holder.num.setText(String.valueOf(foodDomains.get(position).getNumberInCard()));

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);


        holder.PlusItem.setOnClickListener(v -> managementCart.plusNumberFood(foodDomains, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

        holder.MinusItem.setOnClickListener(v -> managementCart.minusNumberFood(foodDomains, position, () -> {
            notifyDataSetChanged();
            changeNumberItemsListener.changed();
        }));

    }


    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView  title;
        TextView feeForEachItem;
        TextView totalEachItem;
        TextView num;
        ImageView pic;
        ImageView PlusItem;
        ImageView MinusItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.CardtitleText);
            feeForEachItem = itemView.findViewById(R.id.feeEachItem);
            totalEachItem = itemView.findViewById(R.id.totalEachItem);
            pic = itemView.findViewById(R.id.picCard);
            num = itemView.findViewById(R.id.numberItemText);
            PlusItem = itemView.findViewById(R.id.CardPlus);
            MinusItem = itemView.findViewById(R.id.CardMinus);
        }
    }
}