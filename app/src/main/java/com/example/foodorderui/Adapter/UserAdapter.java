package com.example.foodorderui.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorderui.Fragments.BottomSheetFragment;
import com.example.foodorderui.R;
import com.example.foodorderui.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

  // create list of user model
    ArrayList<UserModel> list;
    // get current adapter instance
    Context context;

    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // call the xmy layout
        View view = LayoutInflater.from(context).inflate(R.layout.sample_show_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // get position of each user in list
        UserModel users = list.get(position);
        // set the profile picture of all user as identity
        Picasso.get().load(users.getProfilePic()).placeholder(R.drawable.ic_user).into(holder.imageView);

        // set the user name as identity of the user
        holder.userName.setText(users.getUserName());


        holder.itemView.setOnClickListener(v -> {
           // send all the user data to bottom sheet to see their data
            Bundle args = new Bundle();
            args.putString("name", users.getUserName());
            args.putString("phone", users.getPhone());
            args.putString("email", users.getEmail());
            args.putString("password", users.getPassword());
            args.putString("pic", users.getProfilePic());

            BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
            bottomSheetFragment.setArguments(args);
            FragmentTransaction ft = ((AppCompatActivity) context).getSupportFragmentManager()
                    .beginTransaction();
            bottomSheetFragment.show(ft, bottomSheetFragment.getTag());
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
      // the number of user in the list
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.profile_image);
            userName = itemView.findViewById(R.id.userName);

        }
    }
}
