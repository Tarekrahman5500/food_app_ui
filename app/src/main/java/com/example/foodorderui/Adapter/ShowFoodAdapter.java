package com.example.foodorderui.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodorderui.Domain.FoodDomain;
import com.example.foodorderui.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowFoodAdapter  extends RecyclerView.Adapter<ShowFoodAdapter.ViewHolder>{

    // create list of food_model

    ArrayList<FoodDomain> foodDomainArrayList;

    // get current adapter instance
    Context context;

    // create constructor

    public ShowFoodAdapter(ArrayList<FoodDomain> foodDomainArrayList, Context context) {
         this.foodDomainArrayList = foodDomainArrayList;
         this.context = context;
    }


    @NonNull
    @Override
    public ShowFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // cal xml
        View view = LayoutInflater.from(context).inflate(R.layout.show_sample_food_admin, parent, false);
        return new ShowFoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowFoodAdapter.ViewHolder holder, int position) {

        // get position of each food in list

        FoodDomain foodDomain =  foodDomainArrayList.get(position);

        // set the profile picture of all user as identity
        Picasso.get().load(foodDomain.getPic()).placeholder(R.drawable.ic_user).into(holder.imageView);

        // set the user name as identity of the user
        holder.userName.setText(foodDomain.getTitle());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(context, "name: "+ foodDomain.getTitle(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {

        // the number of user in the list
        return foodDomainArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.food_admin_image);
            userName = itemView.findViewById(R.id.foodName);

        }
    }
}
